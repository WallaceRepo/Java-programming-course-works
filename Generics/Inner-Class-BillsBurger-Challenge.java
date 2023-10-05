package Burger;

public class Store {
    public static void main(String[] args) {
        Meal regularMeal = new Meal();
        System.out.println(regularMeal);
        /*
        coke
            burger         regular $5.00
             drink            coke $1.50
              side           fries $2.00
         */
        Meal USRegularMeal = new Meal(0.68);
        System.out.println(USRegularMeal);
        /*
        coke
    burger         regular $0.00
     drink            coke $1.02
      side           fries $1.36
               Total Due: $2.38
         */
        Meal okMeal = new Meal();
        okMeal.addToppings("ketchup", "Mayo", "Avocado", "Cheddar");
        System.out.println(okMeal);
    }
    /*
  No topping found for Cheddar
    burger         regular $5.00
   TOPPING         KETCHUP $0.00
   TOPPING            MAYO $0.00
   TOPPING         AVOCADO $1.00
     drink            coke $1.50
      side           fries $2.00
               Total Due: $9.50



     */
}
///Separate file
package Burger;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private Burger burger;
    private Item drink;
    private Item side;
    private double price;
    private double conversionRate;
    public Meal() {
        this(1);
    }
    public Meal(double conversionRate) {
        this.conversionRate = conversionRate;
        burger = new Burger("regular");
        drink = new Item("coke", "drink", 1.5);
        System.out.println(drink.name);
        //Now, in this method, notice I'm calling the static method getPrice,
        //but this time I use Item's class name, because this code is outside of the Item class.
        side = new Item("fries", "side", 2.0);
    }
    public double getTotal(){
        double total = burger.getPrice() + drink.price + side.price;
        return Item.getPrice(total, conversionRate);
    }
    @Override
    public String toString() {
        return "%s%n%s%n%s%n%26s$%.2f".formatted(burger, drink, side, "Total Due: ", getTotal());
    }
    public void addToppings(String... selectedToppings){
        burger.addToppings(selectedToppings); /*
        I'll call it add toppings, and support passing 1 or more toppings. call the burger add Toppings method.
        This method simply hands off to (or delegates) the work to the burger instance.
           Again, the Meal class is able to call a private method on the burger class.
        */
    }
          //// Inner Class Item
            private class Item {
                private String name;
                private String type;
                private double price;
        /*
            If I reference price, without any qualifier in the code, it refers to
            the price applicable to the current scope. In this example, I'm in the Item class,
            so price here, in the constructor refers to Item's price, and not Meal's price.
         */
                public Item(String name, String type) {
                   // this(name, type, type.equals("burger") ? price : 0);
                    this(name, type, type.equals("burger") ? Meal.this.price : 0);
                }
                public Item(String name, String type, double price) {
                    this.name = name;
                    this.type = type;
                    this.price = price;
                }

                @Override
                public String toString() {
                    return "%10s %15s $%.2f".formatted(type, name, getPrice(price, conversionRate));// You know you can call a static method, without using the class name, from within
                 //   the class itself, which I do here.
                }
                //  JDK16 gave us the ability to have static members on all nested classes. Let's see what this means.
                private static double getPrice(double price, double rate ) {
                    return price * rate;
                }
            }
         /// Burger Inner Class
         private class Burger extends Item {
        /* Modifier static is redundant for enums'. It's also redundant for interfaces, and records.
            These types are all implicitly static when used as inner types.     */
             private static enum Extra {AVOCADO, BACON, CHEESE, KETCHUP, MAYO, MUSTARD, PICKLES;
                 /*
                 Now notice, I'm switching on the keyword, this. When the getPrice method gets called, it will be called from an instance
                  of one of these "Extra" constants, so I can use this as my switch expression.
                  When you switch on an enum, you use enum constants in the case labels, as I show here.
                  */
                  private double getPrice() {
                      return switch (this) {
                          case AVOCADO -> 1.0;
                          case BACON, CHEESE -> 1.5;
                          default -> 0;
                      };
                  }
             }
              private List<Item> toppings = new ArrayList<>();
              Burger(String name) {
                 super(name, "burger", 5.0);
             }

             public double getPrice() {
                 // return super.price;
                 double total = super.price;
                 for ( Item topping : toppings) {
                     total += topping.price;
                 }
                 return total;
             }
             private void addToppings(String... selectedToppings){

                  for ( String selectedTopping : selectedToppings) {
                      try {
                          Extra topping = Extra.valueOf(selectedTopping.toUpperCase());
                          toppings.add(new Item(topping.name(), "TOPPING", topping.getPrice())); /*
                      Why can I access a private method on the enum from this code? Again, this has to do with the special nature of inner types.
                    Private attributes and methods are available to the enclosing class, and this is true for inner types like enums and records, as well as inner classes.
                      */
                      } catch (IllegalArgumentException ie){
                          System.out.println("No topping found for " +  selectedTopping);
                      }
                  }
             }
             public String toString (){
                  StringBuilder itemized = new StringBuilder(super.toString());
                  for ( Item topping : toppings) {
                      itemized.append("\n");
                      itemized.append(topping);
                  }
                  return itemized.toString();
             }
         }

}
