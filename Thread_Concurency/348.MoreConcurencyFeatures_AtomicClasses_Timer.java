package ConcurrencyExtra;

import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

record Student ( String name, int enrolledYear, int studentId ) implements Comparable<Student> {
    @Override
    public int compareTo(Student o ) {
        return this.studentId - o.studentId;
    }
}
class StudentId {
    private int id = 0;
    public int getId(){
        return id;
    }
    public synchronized int getNextId() {
        return ++id;
    }
}
class AtomicStudentId {
    private final AtomicInteger nextId = new AtomicInteger(0);
    public int getId() {
        return nextId.get();
    }
    public int getNextId() {
        return nextId.incrementAndGet();
    }
}
public class Main {
    private static Random random = new Random();
    private static ConcurrentSkipListSet<Student> studentSet = new ConcurrentSkipListSet<>();

    public static void main( String[] args) {
       // StudentId idGenerator = new StudentId();
        AtomicStudentId idGenerator = new AtomicStudentId();
        Callable<Student> studentMaker = () -> {

            int studentId = idGenerator.getNextId();
            Student student = new Student("Tim " + studentId, random.nextInt(2018, 2025), studentId);
            studentSet.add(student);
            return student;
        };
        var executor = Executors.newCachedThreadPool();
        // purpose of seeing why less than 10 students being created or saved in the studentSet, using for loop
        for ( int i = 0; i < 10; i++ ) {
            studentSet.clear();

            try {
                var futures = executor.invokeAll(Collections.nCopies(10, studentMaker));
                System.out.println("# of student = " + studentSet.size());
              //   studentSet.forEach(System.out::println);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
    }
}
// TimerExample.java
package ConcurrencyExtra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TimerExample {
    public static void main(String[] args) {
      //  Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println(threadName + " Timer task executed at: " + formatter.format(LocalDateTime.now()));
            }
        };
  //      timer.scheduleAtFixedRate(task, 0,2000);
        var executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
        try {
            Thread.sleep(12000);
        } catch ( InterruptedException e) {
            e.printStackTrace();
        }
     //   timer.cancel();
        executor.shutdown();
    }
}
/*
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:20
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:22
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:24
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:26
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:28
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:30
pool-1-thread-1 Timer task executed at: 2024-10-10 11:23:32
  */



