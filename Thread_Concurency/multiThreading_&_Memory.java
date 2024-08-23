import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
           StopWatch stopWatch = new StopWatch(TimeUnit.SECONDS);
           Thread green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());

           Thread purple = new Thread(()-> stopWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
           Thread red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());
           green.start();
           purple.start();
           red.start();
    }
}
class StopWatch {
      private TimeUnit timeUnit;
      private int i;
    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
    public void  countDown() {
        countDown(5);
    }
    public void countDown ( int unitCount ) {
        String threadName = Thread.currentThread().getName();
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);

        } catch ( IllegalArgumentException ignore) {

        }
        String color = threadColor.color();
        for (  i =  unitCount; i > 0; i--) {
            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n ", color, threadName, i);
        }
    }
}

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
