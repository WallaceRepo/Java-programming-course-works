package deadlock;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// producer
record Order (long orderId, String item, int qty){

};


public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        ShoeWareHouse wareHouse = new ShoeWareHouse();
        ExecutorService orderingService = Executors.newCachedThreadPool();
        Callable<Order> orderingTask = () -> {
             Order newOrder = generateOrder();
             try {
                 Thread.sleep(random.nextInt(500, 5000));
                 wareHouse.recieveOrder(newOrder);
                  } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
             return newOrder;
        };
//        List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
//        try {
//            orderingService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        try {
            Thread.sleep(random.nextInt(500, 2000));
            for ( int j = 0; j < 15; j++ ) {
                orderingService.submit(()-> wareHouse.recieveOrder(generateOrder()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        orderingService.shutdown();
        try {
            orderingService.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wareHouse.shutDown();
    }
    private static Order generateOrder() {
        return new Order (
                random.nextLong( 1000000, 9999999), ShoeWareHouse.PRODUCT_LIST[random.nextInt(0, 5)],
                random.nextInt(1, 4)
        );
            }
    public static void mainSingleExecutiveThread(String[] args) {
         ShoeWareHouse wareHouse = new ShoeWareHouse();
         var shoeExecutor = Executors.newSingleThreadExecutor();
         shoeExecutor.execute(
                 ()-> {
                     for (int j = 0; j < 10; j++ ) {
                         wareHouse.recieveOrder( new Order (
                                 random.nextLong( 1000000, 9999999), ShoeWareHouse.PRODUCT_LIST[random.nextInt(0, 5)],
                                 random.nextInt(1, 4)
                         ));
                     }
                 }
         );
         // Submit consumer tasks
         for (int i = 0; i < 2; i++) {
             shoeExecutor.execute(() -> {
                 for ( int j = 0; j < 5; j++ ) {
                     Order item = wareHouse.fulfillOrder();
                 }
             });
         }
         shoeExecutor.shutdown();
    }
    public static void mainOld(String[] args) {
            ShoeWareHouse wareHouse = new ShoeWareHouse();
            Thread wareHouseThread = new Thread(() -> {
                for ( int j = 0; j < 10; j++ ) {
                    wareHouse.recieveOrder( new Order (
                            random.nextLong( 1000000, 9999999), ShoeWareHouse.PRODUCT_LIST[random.nextInt(0, 5)],
                            random.nextInt(1, 4)
                    ));
                }
             });
            wareHouseThread.start();
            for ( int i = 0; i< 2; i++ ) {
                Thread consumerThread = new Thread (()-> {
                    for ( int j = 0; j < 5; j++ ) {
                        Order  item = wareHouse.fulfillOrder();
                    }
                });
                consumerThread.start();
            }
    }
}
/*
How the synchronized Keyword Works:
Synchronized Block: When a method is marked as synchronized, it means that a lock is acquired on the object (in this case, ShowWareHouse) before the thread can execute the method. Other threads that try to call any synchronized method on the same object will be blocked until the lock is released.

In recieveOrder():
The method is synchronized, so only one thread can add an order to shippingItems at a time. If the list is full (shippingItems.size() > 20), the thread will call wait(), releasing the lock and waiting until another thread calls notifyAll().
Once there is space in the list (after an order is fulfilled), the waiting thread is notified via notifyAll() and can proceed to add the order.

In fulfillOrder():
Similarly, the method is synchronized, so only one thread can fulfill an order at a time. If the list is empty (shippingItems.isEmpty()), the thread will call wait(), releasing the lock and waiting until an order is added.
Once there are orders to fulfill, notifyAll() is called, and the waiting thread can proceed.
Why Synchronization is Important:
Without synchronized, multiple threads could access and modify the shippingItems list simultaneously, which could lead to issues such as:

Inconsistent state: Two threads might add or remove items at the same time, leading to inconsistent data.
Race conditions: Threads could overwrite each other's changes, causing unpredictable behavior.
In short, the synchronized keyword is what ensures that the access to the shippingItems list is thread-safe.
 */
package deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWareHouse {
     private List<Order> shippingItems;
     private final ExecutorService fulfillmentService;
     public final static String[] PRODUCT_LIST = { "Running Shoes", "Sandals", "Boots","Slippers", "High Tops"};
     public ShoeWareHouse() {
         this.shippingItems = new ArrayList<>();
         fulfillmentService = Executors.newFixedThreadPool(3);
     }
     public void shutDown() {
         fulfillmentService.shutdown();
     }
     public synchronized void recieveOrder(Order item) {
         while(shippingItems.size() > 20 ) {
             try {
                 wait();  // waits until there is space in the shippingItems list
             } catch( InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }
         shippingItems.add(item); // add the order to the list
         System.out.println(Thread.currentThread().getName() + "Incoming: " + item);
         fulfillmentService.submit(this::fulfillOrder); // submit a task to fulfill the order
         notifyAll();// notify other threads (waiting fulfillOrder or recieveOrder) to proceed
     }
    public synchronized Order fulfillOrder () {
        while(shippingItems.isEmpty()) {
            try {
                wait(); // waits until there is an item to fulfill
            } catch( InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order item =  shippingItems.remove(0); // retrieve and remove the first item from the list
        System.out.println(Thread.currentThread().getName() + " Fulfilled: " + item);
        notifyAll(); // notify other threads (waiting recieveOrder) to proceed
        return item;
    }
}
