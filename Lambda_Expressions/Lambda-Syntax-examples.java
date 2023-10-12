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
   T operate ( T value1, T value2 ) {

   }

}
///////////////Main 
package Lambda;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    }
    /// Public Static generic method
    public static <T> T calculator( Operation<T> function, T value1, T value2) {
        T result = function.operate(value1, value2);
        System.out.println("Result of operation: " + result);
        return result;

    }

}
