package Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        String name = "Jen";
        Function<String, String> uCase = String::toUpperCase;
        System.out.println(uCase.apply(name));

        Function<String, String> lastName = s -> s.concat("Brain");
        Function<String, String> uCaseLastName = uCase.andThen(lastName);
        System.out.println(uCaseLastName.apply(name));

        uCaseLastName = uCase.compose(lastName);
        System.out.println(uCaseLastName.apply(name));

        Function<String, String[]> f0 = uCase
                .andThen(s->s.concat("Wallace"))
                .andThen(s->s.split(" "));
        System.out.println(Arrays.toString(f0.apply(name)));

        Function<String, String> f1 = uCase
//                .andThen(s->s.concat("Wallace"))
//                .andThen(s->s.split(" "))
//                .andThen(s->s[1].toUpperCase() + ", " + s[0]);
//        System.out.println(f1.apply(name));
                .andThen(s -> s.concat(" Wallace")) // Convert to uppercase and concatenate " Wallace"
                .andThen(s -> {
                    String[] parts = s.split(" "); // Split the string
                    if (parts.length >= 2) {
                        return parts[1].toUpperCase() + ", " + parts[0]; // Manipulate the array elements
                    } else {
                        return "Invalid format"; // Handling for insufficient parts
                    }
                });

        System.out.println(f1.apply(name));
        Function<String, Integer> f2 = uCase
                .andThen(s->s.concat("Wallace"))
                .andThen(s->s.split(" "))
                .andThen(s->String.join(", ", s))
                .andThen(String::length);
        System.out.println(f2.apply(name));

        String[] names = { "Ann", "Bob", "Carol"};
        Consumer<String> s0 = s->System.out.println(s.charAt(0));
        Consumer<String> s1 = System.out::println;
        Arrays.asList(names).forEach(s0
                .andThen(s->System.out.println(" --"))
                .andThen(s1 ));

        // Consumer Predicate
        Predicate<String> p1 = s ->s.equals("Bob");
        Predicate<String> p2 = s->s.equalsIgnoreCase("Ann");
        Predicate<String> p3 = s->s.startsWith("T");
        Predicate<String> p4 = s->s.endsWith("e");

        Predicate<String> combined1 = p1.or(p2);
        System.out.println("combined " + combined1.test(name));

        Predicate<String> combined2 = p3.and(p4);
        System.out.println("combined2 = " + combined2.test(name));

        Predicate<String> combined3 = p3.and(p4).negate();
        System.out.println("combined3 = " + combined3.test(name));
    }

}
/*
JEN
JENBrain
JENBRAIN
[JENWallace]
WALLACE, JEN
10
A
 --
Ann
B
 --
Bob
C
 --
Carol
combined false
combined2 = false
combined3 = true

Process finished with exit code 0

 */
