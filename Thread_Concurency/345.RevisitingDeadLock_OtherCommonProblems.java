package threadProblem;

import java.io.File;
import java.nio.channels.GatheringByteChannel;
// Below  is Classic DeadLock, in circular wait both thread waiting on each other.
// Dont use synchronized block as your nested locking mechanism;
// Use explicit locks, that have more options
public class Main {
    public static void main(String[] args) {
        File resourceA = new File("InputData.csv");
        File resourceB = new File("output.json");

        Thread threadA = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " attempting to lock resourceA (csv)");
            synchronized (resourceA) {
                System.out.println(threadName + " has lock on resourceA (csv) ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + "NEXT attempting to lock resourceB (json), " + "still has lock on resource A (csv)");
                synchronized (resourceB) {
                    System.out.println(threadName + " has lock on resourceB (json)");
                }
                System.out.println(threadName + " has released lock on resourceB (json)");
            }
            System.out.println(threadName + " has released lock on resourceA(csv) ");
        }, "THREAD-A");

        Thread threadB = new Thread(() -> {
//        String threadName = Thread.currentThread().getName();
//        System.out.println(threadName + " attempting to lock resourceB (json)");
//        synchronized (resourceB) {
//            System.out.println(threadName + " has lock on resourceB (json)");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(threadName + " NEXT attempting to lock resourceA (CSV), " +
//                       "still has lock on resourceB (json)"   );
//                 synchronized (resourceA) {
//                     System.out.println(threadName + " has released lock on resourceA (CSV) ");
//                 }
//        }
//        System.out.println(threadName + " has released lock on resourceB (json)");

            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " attempting to lock resourceA (csv)");
            synchronized (resourceA) {
                System.out.println(threadName + " has lock on resourceA (csv) ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + "NEXT attempting to lock resourceB (json), " + "still has lock on resource A (csv)");
                synchronized (resourceB) {
                    System.out.println(threadName + " has lock on resourceB (json)");
                }
                System.out.println(threadName + " has released lock on resourceB (json)");
            }
            System.out.println(threadName + " has released lock on resourceA(csv) ");
       }, " THREAD_B ");

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch ( InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/*
THREAD-A attempting to lock resourceA (csv)
 THREAD_B  attempting to lock resourceB (json)
THREAD-A has lock on resourceA (csv)
 THREAD_B  has lock on resourceB (json)
 THREAD_B  NEXT attempting to lock resourceA (CSV), still has lock on resourceB (json)
THREAD-ANEXT attempting to lock resourceB (json), still has lock on resource A (csv)

After comming threadB and copy pasted threadA's code. then run:

THREAD-A attempting to lock resourceA (csv)
 THREAD_B  attempting to lock resourceA (csv)
THREAD-A has lock on resourceA (csv)
THREAD-ANEXT attempting to lock resourceB (json), still has lock on resource A (csv)
THREAD-A has lock on resourceB (json)
THREAD-A has released lock on resourceB (json)
THREAD-A has released lock on resourceA(csv)
 THREAD_B  has lock on resourceA (csv)
 THREAD_B NEXT attempting to lock resourceB (json), still has lock on resource A (csv)
 THREAD_B  has lock on resourceB (json)
 THREAD_B  has released lock on resourceB (json)
 THREAD_B  has released lock on resourceA(csv)

Process finished with exit code 0

 */
