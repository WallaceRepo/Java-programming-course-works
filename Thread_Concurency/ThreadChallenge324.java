// Runnable Interface: Defines the task (run() method) that will be executed in a thread.
// Thread Class: Actually creates and runs the thread, invoking the run() method of the Runnable object you pass to it.
// Lambda can be used for runnable interface
// In Java, when a class implements the Runnable interface (like ThreadTwo in your example), it doesn't have the built-in capability to run as a thread on its own. 
// The Runnable interface is simply a contract that ensures the class will have a run() method that contains the code you want to execute concurrently.
// To actually run a Runnable object as a thread, you need to pass it to an instance of the Thread class. The Thread class has the built-in threading capabilities, 
// and it knows how to execute the run() method of the Runnable object you pass to it.

package Concurency_Threads ;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
          System.out.println("Main thread is running");
          ThreadOne threadO = new ThreadOne();

          Runnable runnable = () -> {
              for (int i = 2; i < 20; i++) {
                  System.out.println(i + " ThreadTwo Even");
                  try {
                      Thread.sleep(500);
                  } catch (InterruptedException e) {
                      System.out.println(" Even thread interrupted ");
                      break;
                  }
              }
          };


          ThreadTwo threadT = new ThreadTwo();
          Thread lambda = new Thread(runnable);
         Thread t2 = new Thread(threadT);
         t2.start();
         threadO.start();
         try {
             Thread.sleep(2000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         threadO.interrupt();

    }
}
class ThreadOne extends Thread {
    @Override
    public void run() {
       for ( int i = 1; i < 20; i+=2){
           System.out.println(i + " ThreadOne Odd");
           try {
               Thread.sleep(500);
           } catch (InterruptedException e) {
               System.out.println("Odd thread interrupted");
              break;
           }
       }
    }
}
class ThreadTwo implements Runnable {
    @Override
    public void run() {
        for ( int i = 2; i < 20; i++){
            System.out.println( i + " ThreadTwo Even");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(" Even thread interrupted ");
                break;
            }
        }
    }
}
