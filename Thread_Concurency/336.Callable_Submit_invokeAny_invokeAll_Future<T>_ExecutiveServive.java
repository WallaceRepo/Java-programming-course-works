package executive;
import java.util.List;
import java.util.concurrent.*;

class ColorThreadFactory implements ThreadFactory {
    private String threadName;
    private int colorValue = 1;

    // Constructor that accepts a custom thread name
    public ColorThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    // Default constructor
    public ColorThreadFactory() {
        this.threadName = null; // No specific name provided
    }

    // Constructor that accepts a ThreadColor and uses it as the thread name
    public ColorThreadFactory(ThreadColor color) {
        this.threadName = color.name();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);

        String name = threadName;
        if (name == null) {
            // If no thread name is provided, use the color from the enum
            name = ThreadColor.values()[colorValue].name();
            // Cycle through the colors
            colorValue++;
            if (colorValue > (ThreadColor.values().length - 1)) {
                colorValue = 1; // Reset to start cycling colors again
            }
        }

        // Assign the name to the thread, appending the thread ID for uniqueness
        thread.setName(name + "-" + thread.getId());
        return thread;
    }
}



public class Main {
    public static void main(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> Main.sum(1, 10, 1, "red"),
                () -> Main.sum(10, 100, 10, "blue"),
                () -> Main.sum(2, 10, 2, "green")
        );

        try {
            // First try block for invokeAll
            var results = multiExecutor.invokeAll(taskList);
            for (var result : results) {
                System.out.println(result.get(500, TimeUnit.MILLISECONDS));
            }

            // Second try block for invokeAny
            var resultAny = multiExecutor.invokeAny(taskList);
            System.out.println(resultAny);

        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);

        } finally {
            // Only shut down after all tasks are completed
            multiExecutor.shutdown();
        }
    }
    public static void maininvokeAll(String[] args) {
        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of (
                () -> Main.sum(1,10, 1, "red"),
                 () -> Main.sum(10,100, 10, "blue"),
                 () -> Main.sum(2,10, 2, "green")
        );
        try {
            var results = multiExecutor.invokeAll(taskList);
            for (var result : results) {
                System.out.println(result.get(500, TimeUnit.MILLISECONDS));
            }
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw  new RuntimeException(e);
        }  finally {
            multiExecutor.shutdown();
        }

        // invokeAny
        try {
            var resultsAny = multiExecutor.invokeAny(taskList);
         //   for (var result : resultsAny) { Not an object return is single value, int
                System.out.println(resultsAny);
       //      }
        } catch (InterruptedException | ExecutionException e) {
            throw  new RuntimeException(e);
        }  finally {
            multiExecutor.shutdown();
        }
        }
       //  execute method knows running Runnable which returns no value
     // submit method knows running Callable which can return value from task or thread.
    public static void cachedmain(String[] args) {
         var multiExecutor = Executors.newCachedThreadPool();
         try {
             var redValue = multiExecutor.submit(
                   () -> Main.sum(1, 10, 1, "red"));
             var blueValue = multiExecutor.submit(
                     ()-> Main.sum(10, 100, 10, "blue"));
             var greenValue =multiExecutor.submit(
                     ()->Main.sum( 2, 20, 2, "green"));
              try {
                  System.out.println(redValue.get(500, TimeUnit.SECONDS));
                  System.out.println(blueValue.get(500, TimeUnit.SECONDS));
                  System.out.println(greenValue.get(500, TimeUnit.SECONDS));
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
//             multiExecutor.execute(
//                     () -> Main.sum(1, 10, 1, "yellow"));
//             multiExecutor.execute(
//                     ()-> Main.sum(10, 100, 10, "cyan"));
//             multiExecutor.execute(
//                     ()->Main.sum( 2, 20, 2, "purple"));

//             try {
//                 TimeUnit.SECONDS.sleep(1);
//             } catch (InterruptedException e) {
//                 throw new RuntimeException(e);
//             }
//
//             System.out.println("Next Tasks Will ger executed ");
//             for ( var color: new String[] {"red", "blue", "green", "yellow", "cyan" } ) {
//                 multiExecutor.execute(() -> Main.sum(1, 100, 1, color));
//                }
             }  finally {
             multiExecutor.shutdown();
         }
    }
     // Fixed Thread pool is fixed number of threads regardless of number of tasks
    public static void fixedmain(String[] args) {
         int count = 6;
         var multiExecutor = Executors.newFixedThreadPool ( 3, new ColorThreadFactory());
         for ( int i = 0; i < count; i++ ) {
             multiExecutor.execute(Main::countDown);
        }
         multiExecutor.shutdown();
    }
    public static void singlemain(String[] args) {
        var blueExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_BLUE.name()));
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

    private static int sum( int start, int end, int delta, String colorString ) {
         var threadColor = ThreadColor.ANSI_RESET;
         try {
             threadColor = ThreadColor.valueOf("ANSI_" + colorString.toUpperCase());
         } catch (IllegalArgumentException ignore) {
                // User may pass wrong color name, Will just ignore it
         }

         String color = threadColor.color();
         int sum = 0;
         for ( int i = start; i <= end; i += delta ) {
             sum += i;
         }
         System.out.println(color + Thread.currentThread().getName() + ", " + colorString + "  " + sum);
         return  sum;
    }
}
