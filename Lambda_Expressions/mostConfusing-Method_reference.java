package Lambda;

import javax.print.DocFlavor;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;
import java.util.function.*;

/*
 A Type Reference refers to a class name,an interface name, an enum name, or a record name.
 Remember that static methods are usually called using Type References, but can also be called by instances in our code

 This is NOT true however for method references. Static methods, in method references and lambda expressions,
 must be invoked using a reference type only.

 There are 2 ways to call an instance method: Bounded and Unbounded receiver

 */
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
    PlainOld newPojo = reference1.get();

    /// Creating multiple instances
    PlainOld[] pojo = seedArray(PlainOld::new, 10);


    // Continue Chapter 192 Most confusing Method Reference
        // Below 3 codes show all same results

        calculator((s1, s2) -> s1 + s2, "Hello", "World");
        calculator((s1, s2) -> s1.concat(s2), "Hello", "World");
        // below is the Method Reference way of coding the above
        calculator(String::concat, "Hello", "World");

        BinaryOperator<String> b1 = ( s1, s2 ) -> s1.concat(s2);
        BiFunction<String, String, String> b2 = String::concat;

       // UnaryOperator<String> u1 = String::concat; // this wont work
       // UnaryOperator<String> u2 = (s1, s2) -> s1.concat(s2); // wont work

        UnaryOperator<String> u1 = String::toUpperCase;

        System.out.println(b1.apply("Hello", "World"));
        System.out.println(b2.apply("Hello", "World"));
        System.out.println(u1.apply("Hello"));

        // Another method on the String

        String result = " Hello".transform(u1);
        System.out.println(result);

        result = result.transform(String::toLowerCase);
        System.out.println(result);

        Function<String, Boolean> f0 = String::isEmpty;
        boolean resultBoolean = result.transform(f0);
        System.out.println("Result = " + resultBoolean);


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
