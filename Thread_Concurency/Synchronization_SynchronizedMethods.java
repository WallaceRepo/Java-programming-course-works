package Sync;
// Thread interferance, volatile, atomic operations, synchronized keyword, interleaving thread
//
public class BankAccount {
      private double balance;
      public BankAccount(  double balance) {
          this.balance = balance;
      }
      public double getBalance()
      {
          return balance;
      }
     public  void deposit(double amount) {
          try {
              System.out.println("Deposit - Talking to the teller at the bank...");
              Thread.sleep(7000);
          } catch ( InterruptedException e) {
              throw new RuntimeException(e);
          }
          // critical section is the code that's referencing shared resource kind of like variable.
         // Only one thread at a time should be able to execute critical section kind of code block.
         // When all critical sections are synchronized, the class is thread safe.
         // synchronized keyword on method  blocks
         // So be careful to where to place synchronized keyword to place in code because it blocks and others wait for its finishing task. 
          synchronized (this) {
              double origBalance = balance;
              balance += amount;
              System.out.printf("STARTING BALANCE: %.0f, DEPOSIT (%.0f)" + "  : NEW BALANCE = %.0f%n" , origBalance, amount, balance);
          }

     }
     // synchronized keyword on method. When one thread is executing synch method for an object, all other threads that invoke
    // synch methods for the same object block, and suspend their execution until the first is done with object. That's how it ensures not to interleave.
    public synchronized void withdraw(double amount) {
        try {
            Thread.sleep(100);
        } catch ( InterruptedException e) {
            throw new RuntimeException(e);
        }
        double origBalance = balance;
        if ( amount <= balance ) {
            balance -= amount;
            System.out.printf("STARTING BALANCE: %.0f, WITHDRAWAL (%.0f)" + "  : NEW BALANCE = %.0f%n" , origBalance, amount, balance);
        } else {
            System.out.printf("Starting balance: %.0f, Withdawal ( %.0f)" + ": Insufficient funds!", origBalance, amount);
        }
    }
}


/// Main.java
package Sync;

public class Main {
    public static void main(String[] args) {

        BankAccount companyAccount = new BankAccount(10000);

        Thread thread1 = new Thread(()-> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(()-> companyAccount.deposit(5000));
        Thread thread3 = new Thread(()-> companyAccount.withdraw(2500));
        Thread thread4= new Thread(()-> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final balance: " + companyAccount.getBalance());
    }
}
