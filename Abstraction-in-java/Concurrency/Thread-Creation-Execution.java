package Concurency_Threads;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        var currentThread = Thread.currentThread();
        System.out.println(currentThread.getClass().getName());
        System.out.println(currentThread);
        printThreadState(currentThread);

        currentThread.setName("MainGuy");
        currentThread.setPriority(Thread.MAX_PRIORITY);

        CustomThread customThread = new CustomThread();
        customThread.run(); // runs synchronously
        customThread.start(); // runs asynchronously

        Runnable myRunnable = () -> {
            for (int i = 1; i <= 8; i++) {
                System.out.print("2");
                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();

        for (int i = 1; i <= 3; i++) {
            System.out.println("0");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printThreadState(Thread thread) {
        System.out.println("--------------");
        System.out.println("Thread ID: " + thread.getId());
        System.out.println("Thread Name: " + thread.getName());
        System.out.println("Thread Priority: " + thread.getPriority());
        System.out.println("Thread State: " + thread.getState());
        System.out.println("Thread Group: " + thread.getThreadGroup());
        System.out.println("Thread Is Alive: " + thread.isAlive());
        System.out.println("--------------");
    }
}
package Concurency_Threads;

public class CustomThread extends Thread {
    @Override
    public void run() {
        for ( int  i = 1; i <= 5; i++) {
            System.out.print('1');
            try {
                Thread.sleep(500);
            } catch ( InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
