package executive;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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

     // Fixed Thread pool is fixed number of threads regardless of number of tasks
    public static void main(String[] args) {
         int count = 6;
         var multiExecutor = Executors.newFixedThreadPool ( count, new ColorThreadFactory());
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
}

