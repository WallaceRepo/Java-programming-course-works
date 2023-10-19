package Lambda;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;
import java.util.function.*;

//  I've covered using method references for instance methods, static methods, as well as constructors.
public class Main {
        public static void main(String[] args) {
        // Using Arrays
        String[] name = {"Anna", "Bob", "Ed", "Gary", "David"};

        List<String> list = new ArrayList<>(List.of(name));


        // Method reference
        list.forEach(System.out::println);

        calculator((a,b) -> a + b, 10, 25);
        calculator(Integer::sum, 10, 25);

        calculator((a,b) -> a + b, 10.5, 25.7);
        calculator(Double::sum, 10.6, 25.3);

        Supplier<PlainOld> reference = () -> new PlainOld();

        Supplier<PlainOld> reference1 = PlainOld::new;
        /*
        This method reference is a special type, a constructor method reference. If I run this code like this,
       nothing happens though. I didn't see my constructor executed, or see a statement that a Plain old object
       was created. Why not? Well, a method reference, like lambda expression variables, is sort of like
       a method declaration. It's created and then used at a later time. It's not immediately executed at the time
       it's declared, it's deferred, and the code snippet gets passed around. When you create variables that are
       lambda expressions or method references, it's important to remember that the code isn't invoked at that point.
       The statement or code block gets invoked at the point in the code that the targeted functional method is called.
         */

        PlainOld newPojo = reference1.get();

        /// Creating multiple instances 
            PlainOld[] pojo = seedArray(PlainOld::new, 10);

    }
     ////End of Main method
    private static <T> void calculator ( BinaryOperator<T> function, T value1, T value2) {
            T result = function.apply(value1, value2 );
            System.out.println("Result of operation: " + result);
    }
    // private static method that returns an array of Plain old instances.
    private static PlainOld[] seedArray( Supplier<PlainOld> reference, int count) {
            PlainOld [] array = new PlainOld[count];
            // This code will assign every element in the array,
        // whatever is the result of executing get, for the lambda expression passed to this method.
            Arrays.setAll(array, i -> reference.get());
            return array;
    }
}
class PlainOld {
    private static int last_id = 1;
    private int id;
    public PlainOld() {
        id = PlainOld.last_id++;
        System.out.println("Creating a PlainOld Object " + id);
    }
}
