package Lambda;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

public record Main() {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("alpha", "bravo", "charlie", "delta"));

        for (String s : list) {
            System.out.println(s);
        }

        /// Rewrited above using Lambda expressions.
        System.out.println("--------------");
        list.forEach((s) -> System.out.println(s));

        /// More About Using Lambda Expressions

        System.out.println("-------------");
        String prefix = "nato";
       // String myString = "enclosing box variable";
        list.forEach((var myString) -> {
            char first = myString.charAt(0);
            System.out.println( prefix + " " + myString + " means " + first);
        });

        /// Using calculator method.
        // Overrides method in Operation (Lambda)
        /* Any operation can be executed by my calculator method, on any two elements of the same type,
        as I'm showing you with these Strings. I don't have to write a calculator method for
        every single type I want to support, and I don't have to write every possible operation in my code
        that I think calculator should support. Instead, the code that gets executed,
        can be anything that can be passed as a lambda, and that's pretty cool.
        This frees you up to write code once, that can be easily extendable.
        And it frees up client code, to have more functionality, which can be customized by lambdas. */
        int result = calculator((a,b) -> a + b, 5, 2);
        var result2 = calculator((a,b) -> a / b, 10.0, 2.5);
        var result3 = calculator((a, b)-> a.toUpperCase() + " " + b.toUpperCase(), "Ralph", "Kramen");
//        If you use an explicit type for one parameter, you must use explicit types for all the parameters.
//       If you use var for one parameter, you must use var for all parameters.
//        You can't mix and match var with explicit types, or omit var from one or some of the parameters.
//       When not using curly braces, the return key word is unnecessary, and will throw a compiler error.
//     If you do use a statement block, meaning you use the curly braces,
//    a return statement is required, if the function method has a return type.

        int result4 = calculator((var a, var b) -> {
            var c = a + b; return c;
        }, 5, 2);

        /// example that uses both Consumer and BiConsumer
        var coords = Arrays.asList(
                new double[]{47.2, -95.2},
                new double[]{29.15, -89.24},
                new double[]{35,15, -90.06}
        );
        coords.forEach(s -> System.out.println(Arrays.toString(s)));

         // lambda expression, can be assigned to a local variable.
        BiConsumer<Double, Double> river = (lat, lng)-> System.out.printf("[lat: %.3f lon:%.3f]%n", lat, lng);
        // Above doesnt print anything so, I'll use forEach and local Var
        System.out.println("-----------");
        var firstPoint = coords.get(0);
        processPoint(firstPoint[0], firstPoint[1], river);
        coords.forEach(s -> processPoint(s[0], s[1], river));
        //  Created Nested Lambda expressions variable by taking river's assignment part directly onto above func
        coords.forEach(s -> processPoint(s[0], s[1], (lat, lng)-> System.out.printf("[lat: %.3f lon:%.3f]%n", lat, lng)));
        ///
        // Predicate Functional Interface
        list.removeIf( s -> s.equalsIgnoreCase("bravo"));
        // equalsIgnoreCase method compares two strings, ignoring lower case and upper case differences.
        // This method returns true if the strings are equal, and false if not.
        list.addAll(List.of("echo", "easy", "earnest"));
        System.out.println("-----------");
        list.removeIf( s -> s.startsWith("ea"));
        list.forEach(s -> System.out.println(s));

    }
    /// Public Static generic method
    public static <T> T calculator( Operation<T> function, T value1, T value2) {
        T result = function.operate(value1, value2);
        System.out.println("Result of operation: " + result);
        return result;

    }
       //// 4 Basic category Functional Interfaces in Java.util.func and this package has 40+ functional interfaces
    // Consumer, Function, Predicate, Supplier
//    public static <T> T calculator(BinaryOperator<T> function, T value1, T value2) {
//        T result = function.apply(value1, value2);
//        System.out.println("Result of operation: " + result);
//        return result;
//
//    }
    // Method that uses Consumer Funcitonal interface
    public static <T> void processPoint(T t1, T t2, BiConsumer<T,T> consumer) {
        consumer.accept(t1, t2);
    }
    // Predicate Functional Interface category example


}
////////////

///////
package Lambda;
   /// Functional Interface
//generic interface, meaning I'll include a type parameter.
//For this type, I won't include any bounds, so this interface can be used with any type.
@FunctionalInterface
public interface Operation <T> {

    // I'll add a single abstract method, called operate that returns T, and takes two values,
       //also type T, so value 1 and value 2. This means this method takes two
       //arguments of the same type, and returns a value, also the same type as the values.
       //This interface is a functional interface. It has one single abstract method.
   T operate ( T value1, T value2 );

}

/* 
alpha
bravo
charlie
delta
--------------
alpha
bravo
charlie
delta
-------------
nato alpha means a
nato bravo means b
nato charlie means c
nato delta means d
Result of operation: 7
Result of operation: 4.0
Result of operation: RALPH KRAMEN
Result of operation: 7
[47.2, -95.2]
[29.15, -89.24]
[35.0, 15.0, -90.06]
-----------
[lat: 47.200 lon:-95.200]
[lat: 47.200 lon:-95.200]
[lat: 29.150 lon:-89.240]
[lat: 35.000 lon:15.000]
[lat: 47.200 lon:-95.200]
[lat: 29.150 lon:-89.240]
[lat: 35.000 lon:15.000]
-----------
alpha
charlie
delta
echo
*/
