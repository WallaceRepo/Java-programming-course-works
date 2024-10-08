package SchedulingTasks;

import jdk.jshell.PersistentSnippet;

import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class VisitorList {
    private static final CopyOnWriteArrayList<Person> masterList;
      static {
        masterList = Stream.generate(Person::new)
                .distinct()
                .limit(2500)
                .collect(CopyOnWriteArrayList::new, CopyOnWriteArrayList::add, CopyOnWriteArrayList::addAll);
           }
           private static final ArrayBlockingQueue<Person> newVisitors =  new ArrayBlockingQueue<>(5);

           public static void main(String[] args) {
              // Producer Runnable
               Runnable producer = () -> {
                  Person visitor = new Person();
                  System.out.println("Queueing  " + visitor);
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

               // Consumer Runnable
         Runnable consumer = () -> {
             String threadName = Thread.currentThread().getName();
             System.out.println(threadName + " Polling queue " + newVisitors.size());
             Person visitor = null;
             try {
                // visitor = newVisitors.poll(5, TimeUnit.SECONDS);
                 visitor = newVisitors.take();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
             if ( visitor != null) {
                System.out.println(threadName + " " + visitor);
                if (!masterList.contains(visitor)) {
                    masterList.add(visitor);
                    System.out.println("---> New Visitor gets Coupon!: " + visitor);
                 }
            }
            System.out.println(threadName + " done " + newVisitors.size());
        };
            ScheduledExecutorService producerExecutor = Executors.newSingleThreadScheduledExecutor();
              producerExecutor.scheduleWithFixedDelay(producer, 0, 3, TimeUnit.SECONDS);

           ScheduledExecutorService consumerPool = Executors.newScheduledThreadPool(3);
            for ( int i = 0; i < 3; i++) {
                consumerPool.scheduleAtFixedRate(consumer, 6, 1, TimeUnit.SECONDS);
            }

            while (true) {
                  try {
                      if ( !producerExecutor.awaitTermination(10, TimeUnit.SECONDS))
                       break;
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }

            }
               producerExecutor.shutdownNow();
            while (true) {
                 try {
                   if ( !consumerPool.awaitTermination(3, TimeUnit.SECONDS))
                    break;
                 } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }

            }
               consumerPool.shutdownNow();
        }
}

/// Person.java
package SchedulingTasks;

import java.util.Random;

public record Person(String firstName, String lastName, int age) {
    private final static String[] firsts = {"Able", "Bob", "Charlie", "Donna", "Eve", "Fred"};
    private final static String[] lasts = {"Norton", "Peterson", "Quincy", "Richardson", "Smith"};
    private final static Random random = new Random();

    public Person() {
        this(firsts[random.nextInt(firsts.length)],
                lasts[random.nextInt(lasts.length)],
                random.nextInt(18, 100));
    }

    public String toString() {
        return "%s, %s, (%d)".formatted(lastName, firstName, age);

    }

}
/// main.java
package SchedulingTasks;


import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

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

/*
Total = 10000
{main=1024, thread_1=768, thread_10=640, thread_11=512, thread_12=512,
thread_13=1152, thread_15=912, thread_2=768, thread_3=640, thread_4=512,
thread_5=640, thread_6=256, thread_7=640, thread_8=512, thread_9=512}
ThreadCounts = 10000
 */
