enum Topping {
    MUSTARD, PICKLES, BACON, TOMATO, CHEDDAR;

    public double getPrice(){
         return switch (this) {
             case BACON -> 1.5;
             case CHEDDAR -> 1.0;
             default -> 0.0;
         };
    }
}
public class enums {
    public static void main(String[] args) {
         // Looping through enum
        for (Topping topping : Topping.values()) { // Enhanced for loop inside: Topping.value() returns array of all enum constants
            System.out.println(topping.name() + " : " + topping.getPrice());
        }
    }
}
//        MUSTARD : 0.0
//        PICKLES : 0.0
//        BACON : 1.5
//        TOMATO : 0.0
//        CHEDDAR : 1.0
