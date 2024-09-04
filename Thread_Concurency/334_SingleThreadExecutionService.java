package executive;

public class Main {
    public static void main(String[] args) {
          Thread blue = new Thread( Main:: countDown, ThreadColor.ANSI_BLUE.name());
          Thread yellow = new Thread(Main::countDown, ThreadColor.ANSI_YELLOW.name());
          Thread red = new Thread(Main::countDown, ThreadColor.ANSI_RED.name());
          blue.start();
          yellow.start();
          red.start();

        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void countDown(){
         String threadName = Thread.currentThread().getName();;
         var threadColor = ThreadColor.ANSI_RESET;
         try {
             threadColor = threadColor.valueOf(threadName.toUpperCase());
         } catch (IllegalArgumentException e) {
             // User may pass a bad color name, will just ignore this error.
         }
         String color  = threadColor.color();
         for (int i = 20; i >=0; i--) {
             System.out.println(color + " " + threadName.replace("ANSI ", " ") + "  " + i );
         }
    }
}
package executive;

public enum  ThreadColor {
    ANSI_RESET("\u001B[0m"),
    ANSI_BLACK("\u001B[30m"),
    ANSI_WHITE("\u001b[37m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_CYAN("\u001B[36m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_RED("\u001B[31m"),
    ANSI_YELLOW("\u001B[33m");
    private final String color;

    ThreadColor(String color) {
        this.color = color;
    }
    public String color() {
        return color;
    }
}

/*
 ANSI_BLUE  20
 ANSI_RED  20
 ANSI_YELLOW  20
 ANSI_RED  19
 ANSI_BLUE  19
 ANSI_RED  18
 ANSI_BLUE  18
 ANSI_YELLOW  19
 ANSI_BLUE  17
 ANSI_RED  17
 ANSI_BLUE  16
 ANSI_YELLOW  18
 ANSI_BLUE  15
 ANSI_RED  16
 ANSI_BLUE  14
 ANSI_RED  15
 ANSI_YELLOW  17
 ANSI_RED  14
 ANSI_YELLOW  16
 ANSI_RED  13
 ANSI_BLUE  13
 ANSI_RED  12
 ANSI_YELLOW  15
 ANSI_RED  11
 ANSI_BLUE  12
 ANSI_RED  10
 ANSI_YELLOW  14
 ANSI_BLUE  11
 ANSI_YELLOW  13
 ANSI_RED  9
 ANSI_YELLOW  12
 ANSI_BLUE  10
 ANSI_YELLOW  11
 ANSI_BLUE  9
 ANSI_YELLOW  10
 ANSI_RED  8
 ANSI_BLUE  8
 ANSI_YELLOW  9
 ANSI_RED  7
 ANSI_YELLOW  8
 ANSI_BLUE  7
 ANSI_YELLOW  7
 ANSI_RED  6
 ANSI_YELLOW  6
 ANSI_RED  5
 ANSI_BLUE  6
 ANSI_YELLOW  5
 ANSI_BLUE  5
 ANSI_YELLOW  4
 ANSI_RED  4
 ANSI_YELLOW  3
 ANSI_BLUE  4
 ANSI_YELLOW  2
 ANSI_RED  3
 ANSI_YELLOW  1
 ANSI_BLUE  3
 ANSI_YELLOW  0
 ANSI_RED  2
 ANSI_BLUE  2
 ANSI_RED  1
 ANSI_BLUE  1
 ANSI_RED  0
 ANSI_BLUE  0
  */

package executive;

public class Main {
    public static void main(String[] args) {
          Thread blue = new Thread( Main:: countDown, ThreadColor.ANSI_BLUE.name());
          Thread yellow = new Thread(Main::countDown, ThreadColor.ANSI_YELLOW.name());
          Thread red = new Thread(Main::countDown, ThreadColor.ANSI_RED.name());
      // Changed above main() method's yellow and red thread to wait while blue is running by join() called next to start()
          blue.start();
        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
          yellow.start();
        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
          red.start();
       try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void countDown(){
         String threadName = Thread.currentThread().getName();;
         var threadColor = ThreadColor.ANSI_RESET;
         try {
             threadColor = threadColor.valueOf(threadName.toUpperCase());
         } catch (IllegalArgumentException e) {
             // User may pass a bad color name, will just ignore this error.
         }
         String color  = threadColor.color();
         for (int i = 20; i >=0; i--) {
             System.out.println(color + " " + threadName.replace("ANSI ", " ") + "  " + i );
         }
    }
}
/*
 ANSI_BLUE  20
 ANSI_BLUE  19
 ANSI_BLUE  18
 ANSI_BLUE  17
 ANSI_BLUE  16
 ANSI_BLUE  15
 ANSI_BLUE  14
 ANSI_BLUE  13
 ANSI_BLUE  12
 ANSI_BLUE  11
 ANSI_BLUE  10
 ANSI_BLUE  9
 ANSI_BLUE  8
 ANSI_BLUE  7
 ANSI_BLUE  6
 ANSI_BLUE  5
 ANSI_BLUE  4
 ANSI_BLUE  3
 ANSI_BLUE  2
 ANSI_BLUE  1
 ANSI_BLUE  0
 ANSI_YELLOW  20
 ANSI_YELLOW  19
 ANSI_YELLOW  18
 ANSI_YELLOW  17
 ANSI_YELLOW  16
 ANSI_YELLOW  15
 ANSI_YELLOW  14
 ANSI_YELLOW  13
 ANSI_YELLOW  12
 ANSI_YELLOW  11
 ANSI_YELLOW  10
 ANSI_YELLOW  9
 ANSI_YELLOW  8
 ANSI_YELLOW  7
 ANSI_YELLOW  6
 ANSI_YELLOW  5
 ANSI_YELLOW  4
 ANSI_YELLOW  3
 ANSI_YELLOW  2
 ANSI_YELLOW  1
 ANSI_YELLOW  0
 ANSI_RED  20
 ANSI_RED  19
 ANSI_RED  18
 ANSI_RED  17
 ANSI_RED  16
 ANSI_RED  15
 ANSI_RED  14
 ANSI_RED  13
 ANSI_RED  12
 ANSI_RED  11
 ANSI_RED  10
 ANSI_RED  9
 ANSI_RED  8
 ANSI_RED  7
 ANSI_RED  6
 ANSI_RED  5
 ANSI_RED  4
 ANSI_RED  3
 ANSI_RED  2
 ANSI_RED  1
 ANSI_RED  0 
  */
// Executors.newSingleThreadExecutor()
// What it Returns:
// This method returns an instance of ExecutorService, which is an interface. The actual object returned is typically an instance of a class 
//  that implements ExecutorService, such as ThreadPoolExecutor.
// ExecutorService is a subinterface of Executor and provides methods for managing the lifecycle of tasks and the executor itself.
// Key Methods:
// execute(Runnable command): Executes the given command at some time in the future.
// shutdown(): Initiates an orderly shutdown of the executor, where previously submitted tasks are executed, but no new tasks will be accepted.
// 

package executive;

import javax.swing.plaf.ColorUIResource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class ColorThreadFactory implements ThreadFactory {
    private String threadName;
    public ColorThreadFactory(ThreadColor color) {
        this.threadName = color.name();
    }
    public  Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}
public class Main {
    public static void main(String[] args) {
        var blueExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_BLUE));
        blueExecutor.execute(Main::countDown);
        blueExecutor.shutdown();
        boolean isDone = false;
        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       if (isDone) {
            System.out.println("Blue finished, started Yellow");
           var yellowExecutor= Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_YELLOW));
           yellowExecutor.execute(Main::countDown);
           yellowExecutor.shutdown();

           try {
               isDone = yellowExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           if (isDone) {
               System.out.println("Yellow finished, starting Red");
               var redExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_RED));
               redExecutor.execute(Main::countDown);
               redExecutor.shutdown();
               try {
                   isDone = redExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               if(isDone) {
                   System.out.println("All processes completed");
               }
           }
        }
    }
    public static void notmain(String[] args) {
          Thread blue = new Thread( Main:: countDown, ThreadColor.ANSI_BLUE.name());
          Thread yellow = new Thread(Main::countDown, ThreadColor.ANSI_YELLOW.name());
          Thread red = new Thread(Main::countDown, ThreadColor.ANSI_RED.name());
          blue.start();
        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
          yellow.start();
        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
          red.start();
       try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void countDown(){
         String threadName = Thread.currentThread().getName();;
         var threadColor = ThreadColor.ANSI_RESET;
         try {
             threadColor = threadColor.valueOf(threadName.toUpperCase());
         } catch (IllegalArgumentException e) {
             // User may pass a bad color name, will just ignore this error.
         }
         String color  = threadColor.color();
         for (int i = 20; i >=0; i--) {
             System.out.println(color + " " + threadName.replace("ANSI ", " ") + "  " + i );
         }
    }
}
/* 
 ANSI_BLUE  20
 ANSI_BLUE  19
 ANSI_BLUE  18
 ANSI_BLUE  17
 ANSI_BLUE  16
 ANSI_BLUE  15
 ANSI_BLUE  14
 ANSI_BLUE  13
 ANSI_BLUE  12
 ANSI_BLUE  11
 ANSI_BLUE  10
 ANSI_BLUE  9
 ANSI_BLUE  8
 ANSI_BLUE  7
 ANSI_BLUE  6
 ANSI_BLUE  5
 ANSI_BLUE  4
 ANSI_BLUE  3
 ANSI_BLUE  2
 ANSI_BLUE  1
 ANSI_BLUE  0
Blue finished, started Yellow
 ANSI_YELLOW  20
 ANSI_YELLOW  19
 ANSI_YELLOW  18
 ANSI_YELLOW  17
 ANSI_YELLOW  16
 ANSI_YELLOW  15
 ANSI_YELLOW  14
 ANSI_YELLOW  13
 ANSI_YELLOW  12
 ANSI_YELLOW  11
 ANSI_YELLOW  10
 ANSI_YELLOW  9
 ANSI_YELLOW  8
 ANSI_YELLOW  7
 ANSI_YELLOW  6
 ANSI_YELLOW  5
 ANSI_YELLOW  4
 ANSI_YELLOW  3
 ANSI_YELLOW  2
 ANSI_YELLOW  1
 ANSI_YELLOW  0
Yellow finished, starting Red
 ANSI_RED  20
 ANSI_RED  19
 ANSI_RED  18
 ANSI_RED  17
 ANSI_RED  16
 ANSI_RED  15
 ANSI_RED  14
 ANSI_RED  13
 ANSI_RED  12
 ANSI_RED  11
 ANSI_RED  10
 ANSI_RED  9
 ANSI_RED  8
 ANSI_RED  7
 ANSI_RED  6
 ANSI_RED  5
 ANSI_RED  4
 ANSI_RED  3
 ANSI_RED  2
 ANSI_RED  1
 ANSI_RED  0
All processes completed 
  */
