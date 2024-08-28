package Sync;
// Thread interferance, volatile, atomic operations, synchronized keyword, interleaving thread
// All synchronized methods, regardless of what they're accessing or doing can't run until they acquire the monitor lock on the current instance.


public class BankAccount {
      private double balance;
      private String name;
      private final Object lockName = new Object();
      private final Object lockBalance = new Object();
      public BankAccount (  String name, double balance) {
          this.balance = balance;
          this.name = name;
      }

    public  void setName(String name) {
          synchronized ( lockName) {
              this.name = name;
              System.out.println("updated name = " + this.name);
          }

    }

    public String getName() {
        return name;
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
         // Below is synchronized statement.
         // An Intrinsic lock or monitor is only available on an object, not on any primitive types.
         // So let's create double local var and give this.balance.
       //  Double boxedBalance = this.balance;
          synchronized (lockBalance) { // here this means current instance. Whether sychronized statement or method both acquired lock
              double origBalance = balance;
              balance += amount;
              System.out.printf("STARTING BALANCE: %.0f, DEPOSIT (%.0f)" + "  : NEW BALANCE = %.0f%n" , origBalance, amount, balance);
             addPromoDollars(amount); // one thread acquired a lock and calling another method. This is the same thread calling a different
              // method, which is also trying to acquire a lock. So it's in the same thread, any nested calls that try to acquire lock wont block.
              // This feature is called Renentrant Synchronization.
          }

     }
    private void addPromoDollars( double amount ) {
        if ( amount >= 5000 ) {
            synchronized (lockBalance ) {
                System.out.println("Congratulations, you earned a promotional deposit ");
                balance += 25;
            }
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

//main.java
package Sync;

public class Main {
    public static void main(String[] args) {

        BankAccount companyAccount = new BankAccount( "Tom", 10000);
       // BankAccount instance acquired lock because of synchronised method and statement in its class.
        // when I use synchronized keyword on a method, the current instance is implicitly locked. Locked means:
        // Every object instance in Java has a built in intrinsic lock, know as monitor lock.
        // A thread acquieres a lock by exexuting a synchronized method on the instance, or by using the instance as the parameter to a synchronized statement.
        // A thread releases a lock when it exits from synch block or method, even if thrown an exception.
        //  Only one threat a time gets this lock which prevents all other threads from accessing the instance's state until lock is released.
        // All other that want to access instance's state through sync code will block and wait and until its done.

        Thread thread1 = new Thread(()-> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(()-> companyAccount.deposit(5000));
        // even though using a synchronized block in the setName method, I'm syncronizing on an instance,
        //  So that's going to get  locked by our long running deposit method. I don't have to synchronize on current instance, the keyword this.
        // Instead of current instance, we can use field name. this.field
        Thread thread3 = new Thread(()-> companyAccount.setName("Tim"));
        Thread thread4= new Thread(()-> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();
        try { Thread.sleep(500); } catch (InterruptedException e) { throw new RuntimeException(e); }
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
