package SchedulingTasks;

// continue

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

/*
Total = 10000
{main=1024, thread_1=768, thread_10=640, thread_11=512, thread_12=512,
thread_13=1152, thread_15=912, thread_2=768, thread_3=640, thread_4=512,
thread_5=640, thread_6=256, thread_7=640, thread_8=512, thread_9=512}
ThreadCounts = 10000
 */
