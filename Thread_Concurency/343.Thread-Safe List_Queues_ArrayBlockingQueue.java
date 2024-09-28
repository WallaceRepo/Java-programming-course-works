package SchedulingTasks;

import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VisitorList {
    private static final ArrayBlockingQueue<Person> newVisitors =  new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {

               Runnable producer = () -> {
                  Person visitor = new Person();
                  System.out.println("Adding " + visitor);
                  boolean queued = false;
                  try {
//                      newVisitors.put(visitor);
//                      queued = true;
                      queued = newVisitors.offer(visitor, 5, TimeUnit.SECONDS);
                  } catch (InterruptedException e) {
                   //   System.out.println("Illegal State Exception!");
                      System.out.println("Interrupted Exception!");
                  }

                  if ( queued ) {
                      System.out.println(newVisitors);
                  } else {
                      System.out.println("Queue is Full, cannot add "  + visitor);
                      System.out.println("Draining Queue and writing data to file");
                      List<Person> tempList = new ArrayList<>();
                      newVisitors.drainTo(tempList);
                      List<String> lines = new ArrayList<>();
                      tempList.forEach((customer) -> lines.add(customer.toString()));
                      lines.add(visitor.toString());

                      try {
                          Files.write(Path.of("DrainedQueue.txt"), lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                      } catch (IOException e) {
                          throw new RuntimeException(e);
                      }
                  }
              };
            ScheduledExecutorService produceExecutor = Executors.newSingleThreadScheduledExecutor();
              produceExecutor.scheduleWithFixedDelay(producer, 0, 1, TimeUnit.SECONDS);

              while (true) {
                  try {
                      if ( !produceExecutor.awaitTermination(20, TimeUnit.SECONDS))
                       break;
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
              }
              produceExecutor.shutdown();
        }
}

/*
Adding Peterson, Donna, (98)
[Peterson, Donna, (98)]
Adding Richardson, Charlie, (40)
[Peterson, Donna, (98), Richardson, Charlie, (40)]
Adding Smith, Fred, (37)
[Peterson, Donna, (98), Richardson, Charlie, (40), Smith, Fred, (37)]
Adding Smith, Donna, (87)
[Peterson, Donna, (98), Richardson, Charlie, (40), Smith, Fred, (37), Smith, Donna, (87)]
Adding Norton, Bob, (82)
[Peterson, Donna, (98), Richardson, Charlie, (40), Smith, Fred, (37), Smith, Donna, (87), Norton, Bob, (82)]
Adding Smith, Charlie, (78)
Queue is Full, cannot add Smith, Charlie, (78)
Draining Queue and writing data to file
Adding Quincy, Eve, (97)
[Quincy, Eve, (97)]
Adding Peterson, Fred, (37)
[Quincy, Eve, (97), Peterson, Fred, (37)]
Adding Peterson, Bob, (41)
[Quincy, Eve, (97), Peterson, Fred, (37), Peterson, Bob, (41)]
Adding Norton, Bob, (99)
[Quincy, Eve, (97), Peterson, Fred, (37), Peterson, Bob, (41), Norton, Bob, (99)]
Adding Norton, Bob, (52)
[Quincy, Eve, (97), Peterson, Fred, (37), Peterson, Bob, (41), Norton, Bob, (99), Norton, Bob, (52)]
Adding Peterson, Bob, (69)
Queue is Full, cannot add Peterson, Bob, (69)
Draining Queue and writing data to file
 */

/*
DrainedQueue.txt file was created and on it:

Peterson, Donna, (98)
Richardson, Charlie, (40)
Smith, Fred, (37)
Smith, Donna, (87)
Norton, Bob, (82)
Smith, Charlie, (78)
Quincy, Eve, (97)
Peterson, Fred, (37)
Peterson, Bob, (41)
Norton, Bob, (99)
Norton, Bob, (52)
Peterson, Bob, (69)
 */


// Main.java
package SchedulingTasks;


import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;
record Person(String firstName, String lastName, int age ) {
    private final static String[] firsts = { "Able", "Bob", "Charlie", "Donna", "Eve" , "Fred"};
    private final static String[] lasts = {"Norton", "Peterson", "Quincy", "Richardson", "Smith"};
    private  final static Random random = new Random();
    public Person() {
        this( firsts [ random.nextInt(firsts.length)],
                lasts [ random.nextInt(lasts.length)],
                random.nextInt(18, 100));
    }
    public String toString() {
        return "%s, %s, (%d)".formatted(lastName, firstName, age);

    }

}
public class Main {
    public static void main(String[] args) {
        var threadMap = new ConcurrentSkipListMap<String, Long>();
         var persons = Stream.generate(Person::new)
                 .limit(10000)
                 .parallel()
                 .peek((p) -> {
                     var threadName = Thread.currentThread().getName()
                             .replace("ForkJoinPool.commonPool-worker-", "thread_");
                     threadMap.merge(threadName, 1L, Long::sum);
                 })
                 .toArray(Person[]::new);

         System.out.println("Total = " + persons.length);
         System.out.println(threadMap);

         long total = 0;
         for ( long count : threadMap.values()) {
             total += count;
         }
         System.out.println("ThreadCounts = " + total);
    }
}


    
