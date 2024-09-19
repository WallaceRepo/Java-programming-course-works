package SchedulingTasks;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        var dtf = DateTimeFormatter.ofLocalizedDateTime(
                FormatStyle.MEDIUM,
                FormatStyle.LONG
        );
        Callable<ZonedDateTime> waitThenDoIt = () -> {
            ZonedDateTime zdt = null;
            try {
                TimeUnit.SECONDS.sleep(4);
                zdt = ZonedDateTime.now();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return zdt;
        };

        var threadPool = Executors.newFixedThreadPool(4);
        List<Callable<ZonedDateTime>> list = Collections.nCopies(4, waitThenDoIt);

        try {
            System.out.println("---> " + ZonedDateTime.now().format(dtf));
            List<Future<ZonedDateTime>> futureList = threadPool.invokeAll(list);
            for (Future<ZonedDateTime> result : futureList) {
                try {
                    System.out.println(result.get(1, TimeUnit.SECONDS).format(dtf));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            threadPool.shutdown();
        }

        Runnable dateTask = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(" a " + ZonedDateTime.now().format(dtf));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        System.out.println("---> " + ZonedDateTime.now().format(dtf));
        // ScheduledExecutorService executer = Executors.newSingleThreadScheduledExecutor();
//        executer.schedule(() -> System.out.println(ZonedDateTime.now().format(dtf)), 2, TimeUnit.SECONDS);
//        executer.shutdown();

        // Below is actually cached Scheduled executor service
        ScheduledExecutorService executer = Executors.newScheduledThreadPool(4);
//        for ( int i = 0; i < 4; i++ ) {
//            executer.schedule(() -> System.out.println(ZonedDateTime.now().format(dtf)), 2 * ( i+1), TimeUnit.SECONDS);
//         }
        var scheduledTask = executer.scheduleAtFixedRate(dateTask,2, 2, TimeUnit.SECONDS);

        var scheduledTask2 = executer.scheduleAtFixedRate(
                ()->System.out.println(" b " + ZonedDateTime.now().format(dtf)), 2, 2,TimeUnit.SECONDS
        );

        long time = System.currentTimeMillis();
        while (!scheduledTask.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(2);
                if ((System.currentTimeMillis() - time) / 1000 > 10) {
                    scheduledTask.cancel(true);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        executer.shutdown();
    }
}
