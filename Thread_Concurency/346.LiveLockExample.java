// Maze.java
package threadProblem;

import java.util.Arrays;

public class Maze {
     public static final int MAZE_SIZE = 4;
     private final String[][] cells = new String[MAZE_SIZE][MAZE_SIZE];
     public Maze() {
         for ( var row : cells ) {
             Arrays.fill(row, "");
         }
     }
     public int[] getNextLocation( int[] lastSpot ) {

         int[] nextSpot = new int[2];
         nextSpot[1] = ( lastSpot[1] == Maze.MAZE_SIZE - 1) ? 0 : lastSpot[1] + 1;
         nextSpot[0] = lastSpot[0];
         if ( nextSpot[1] == 0) {
             nextSpot[0] = ( lastSpot[0] == Maze.MAZE_SIZE -1) ? 0 : lastSpot[0] + 1;

         }
         return  nextSpot;
     }
     public void moveLocation( int locX, int locY, String name ) {
          cells[locX][locY] = name.substring(0,1);
          resetSearchedCells(name);

     }
     public void resetSearchedCells( String name) {
         for (var row : cells) {
             Arrays.asList(row).replaceAll(c -> c.equals("!" + name.charAt(0)) ? "" : c);
         }
     }
     public boolean searchCell( String partner, int[] nextSpot, int[] lastSpot) {
         if ( cells[nextSpot[0]] [nextSpot[1]].equals(partner.substring(0,1))) {
             return true;
         }
         cells[lastSpot[0]][lastSpot[1]] = " !" + partner.charAt(0);
         return false;
     }

    @Override
    public String toString() {
        return Arrays.deepToString(cells);
    }
}

// MazeRunner.java
package threadProblem;

import java.util.Arrays;
import java.util.concurrent.Executors;

record Participant( String name, String searchingFor, Maze maze, int[] startingPosition) {
    Participant { maze.moveLocation(startingPosition[0], startingPosition[1], name);  }
}

class ParticipantThread extends Thread {
    public final Participant participant;

    public ParticipantThread(Participant participant) {
        super(participant.name() );
        this.participant = participant;
    }
    @Override
    public void run() {
        int [] lastSpot = participant.startingPosition();
        Maze maze = participant.maze();

        while (true) {
            int[] newSpot = maze.getNextLocation(lastSpot);
            try {
                Thread.sleep(500);
                if ( maze.searchCell(participant.searchingFor(), newSpot, lastSpot)) {
                    System.out.printf("%s found %s at %s!%n", participant.name(),
                    participant.searchingFor(), Arrays.toString(newSpot));
                    break;
                }
                synchronized (maze) {
                    maze.moveLocation(newSpot[0], newSpot[1], participant.name());
                }
                lastSpot = new int[]{newSpot[0], newSpot[1]};
            } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
                 return;
             }
                System.out.println(participant.name() + " searching " + participant.maze());
             }
        }
}
public class MazeRunner {
    public static void main(String[] args) {
           Maze maze = new Maze();
           Participant  adam = new Participant("Adam", "Grace", maze, new int[]{3, 3});
           Participant grace = new Participant("Grace", "Adam", maze, new int[]{1, 1});
           System.out.println(maze);

           var executer = Executors.newCachedThreadPool();
    //       var adamsResults = executer.submit(new ParticipantThread(adam));
          var graceResult = executer.submit(new ParticipantThread(grace));
           executer.shutdown();
    }
}
/*
 // var adamsResults = executer.submit(new ParticipantThread(adam));
[[, , , ], [, G, , ], [, , , ], [, , , A]]
Adam searching [[A, , , ], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G, A, , ], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G, A, ], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G,  !G, A], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G,  !G,  !G], [A, G, , ], [, , , ], [, , ,  !G]]

//   var graceResult = executer.submit(new ParticipantThread(grace));
[[, , , ], [, G, , ], [, , , ], [, , , A]]
Grace searching [[, , , ], [,  !A, G, ], [, , , ], [, , , A]]
Grace searching [[, , , ], [,  !A,  !A, G], [, , , ], [, , , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [G, , , ], [, , , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [ !A, G, , ], [, , , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [ !A,  !A, G, ], [, , , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [ !A,  !A,  !A, G], [, , , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [ !A,  !A,  !A,  !A], [G, , , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [ !A,  !A,  !A,  !A], [ !A, G, , A]]
Grace searching [[, , , ], [,  !A,  !A,  !A], [ !A,  !A,  !A,  !A], [ !A,  !A, G, A]]
Grace found Adam at [3, 3]!
*/
// Both threads of Adam and Grace searching 
// Updated code, MazeRunner.java
package threadProblem;

import java.util.Arrays;
import java.util.concurrent.Executors;

record Participant( String name, String searchingFor, Maze maze, int[] startingPosition) {
    Participant { maze.moveLocation(startingPosition[0], startingPosition[1], name);  }
}

class ParticipantThread extends Thread {
    public final Participant participant;

    public ParticipantThread(Participant participant) {
        super(participant.name() );
        this.participant = participant;
    }
    @Override
    public void run() {
        int [] lastSpot = participant.startingPosition();
        Maze maze = participant.maze();

        while (true) {
            int[] newSpot = maze.getNextLocation(lastSpot);
            try {
                // Thread.sleep(500);
                // to escape from LiveLock or threads checking on each other's state keep looping, give different times
                Thread.sleep(participant.name().equals("Grace") ? 2900 : 500);
                if ( maze.searchCell(participant.searchingFor(), newSpot, lastSpot)) {
                    System.out.printf("%s found %s at %s!%n", participant.name(),
                    participant.searchingFor(), Arrays.toString(newSpot));
                    break;
                }
                synchronized (maze) {
                    maze.moveLocation(newSpot[0], newSpot[1], participant.name());
                }
                lastSpot = new int[]{newSpot[0], newSpot[1]};
            } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
                 return;
             }
                System.out.println(participant.name() + " searching " + participant.maze());
             }
        }
}
public class MazeRunner {
    public static void main(String[] args) {
           Maze maze = new Maze();
           Participant  adam = new Participant("Adam", "Grace", maze, new int[]{3, 3});
           Participant grace = new Participant("Grace", "Adam", maze, new int[]{1, 1});
           System.out.println(maze);

           var executer = Executors.newCachedThreadPool();
           var adamsResults = executer.submit(new ParticipantThread(adam));
           var graceResult = executer.submit(new ParticipantThread(grace));

        // If both participants are moving to search for each other
            while (!adamsResults.isDone() && !graceResult.isDone()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (adamsResults.isDone()) {
                graceResult.cancel(true);

            } else if (graceResult.isDone()) {
                adamsResults.cancel(true);
            }
           executer.shutdown();
    }
}
/*
[[, , , ], [, G, , ], [, , , ], [, , , A]]
Adam searching [[A, , , ], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G, A, , ], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G, A, ], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G,  !G, A], [, G, , ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G,  !G,  !G], [A, G, , ], [, , , ], [, , ,  !G]]
Grace searching [[ !G,  !G,  !G,  !G], [A,  !A, G, ], [, , , ], [, , ,  !G]]
Adam searching [[ !G,  !G,  !G,  !G], [ !G, A, G, ], [, , , ], [, , ,  !G]]
Adam found Grace at [1, 2]!

Process finished with exit code 0

  */

