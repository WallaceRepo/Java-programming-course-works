package deadlock;

import java.util.ArrayList;
import java.util.List;

public class ShowWareHouse {
     private List<Order> shippingItems;
     public final static String[] PRODUCT_LIST = { "Running Shoes", "Sandals", "Boots","Slippers", "High Tops"};
     public ShowWareHouse() {
         this.shippingItems = new ArrayList<>();
     }
     public synchronized void recieveOrder(Order item) {
         while(shippingItems.size() > 20 ) {
             try {
                 wait();
             } catch( InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }
         shippingItems.add(item);
         System.out.println("Incoming: " + item);
         notifyAll();
     }
    public synchronized Order fulfillOrder () {
        while(shippingItems.isEmpty()) {
            try {
                wait();
            } catch( InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Order item =  shippingItems.remove(0); // remove method do both retrieve and remove that element from a list
        System.out.println(Thread.currentThread().getName() + " Fulfilled: " + item);
        notifyAll();
        return item;
    }
}
// Main.java 

package deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// producer
record Order (long orderId, String item, int qty){

};


public class Main {
    private static final Random random = new Random();
    public static void main(String[] args) {
            ShowWareHouse wareHouse = new ShowWareHouse();
            Thread wareHouseThread = new Thread(() -> {
                for ( int j = 0; j < 10; j++ ) {
                    wareHouse.recieveOrder( new Order (
                            random.nextLong( 1000000, 9999999), ShowWareHouse.PRODUCT_LIST[random.nextInt(0, 5)],
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
/* Incoming: Order[orderId=5108898, item=Running Shoes, qty=3]
Thread-1 Fulfilled: Order[orderId=5108898, item=Running Shoes, qty=3]
Incoming: Order[orderId=3113982, item=Running Shoes, qty=1]
Thread-2 Fulfilled: Order[orderId=3113982, item=Running Shoes, qty=1]
Incoming: Order[orderId=5616810, item=Slippers, qty=1]
Thread-1 Fulfilled: Order[orderId=5616810, item=Slippers, qty=1]
Incoming: Order[orderId=4810734, item=High Tops, qty=1]
Incoming: Order[orderId=3020818, item=High Tops, qty=1]
Thread-2 Fulfilled: Order[orderId=4810734, item=High Tops, qty=1]
Thread-2 Fulfilled: Order[orderId=3020818, item=High Tops, qty=1]
Incoming: Order[orderId=7962919, item=Boots, qty=2]
Incoming: Order[orderId=5941538, item=Boots, qty=3]
Incoming: Order[orderId=4300521, item=High Tops, qty=3]
Thread-1 Fulfilled: Order[orderId=7962919, item=Boots, qty=2]
Thread-1 Fulfilled: Order[orderId=5941538, item=Boots, qty=3]
Thread-1 Fulfilled: Order[orderId=4300521, item=High Tops, qty=3]
Incoming: Order[orderId=9272922, item=High Tops, qty=1]
Thread-2 Fulfilled: Order[orderId=9272922, item=High Tops, qty=1]
Incoming: Order[orderId=4685236, item=Boots, qty=3]
Thread-2 Fulfilled: Order[orderId=4685236, item=Boots, qty=3] */
