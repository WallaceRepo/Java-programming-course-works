package Lambda;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public record Main() {

    record Person ( String firstName, String lastName ) {
        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }

    public static void main(String[] args) {

        List<Person> people = new ArrayList<>(Arrays.asList(
                new Main.Person("Lucky", "Van Pelt"),
                new Person ("Sally", "Yellow"),
                new Person ("Peppermint", " Patty"),
                new Person("kiity", "Dog")        ));

          // Anonymous class
        //Anonymous Class
        var comparatorLastName = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.lastName().compareTo(o2.lastName());
            }
        };

        //Instead of assigning an anonymous class to a variable,
        //I'm to both create and use this anonymous class directly in the method argument.
       people.sort(new Comparator<Person>() {
           @Override
           public int compare(Person o1, Person o2) {
               return o1.lastName().compareTo(o2.lastName());
           }
       });

        // Replace above with Lambda Expression
        people.sort((o1, o2) -> o1.lastName().compareTo(o2.lastName));
        System.out.println(people);

      //   people.sort(comparatorLastName);
        System.out.println(people); // [Peppermint  Patty, kiity Dog, Lucky Van Pelt, Sally Yellow]

        ////////// Let's create local interface. This interface will have a single abstract method.
        // This abstract method will be used for Creating Lambda expression.


           // I don't have to implement abstract methods, when I extend interfaces

        interface EnhancedComparator<T> extends Comparator<T> {
            int secondLevel(T o1, T o2);
        }
           /*
           This means there are now two abstract methods, on the EnhancedComparator, that concrete classes have
            to implement, if they use this interface.
            */
        var comparatorMixed = new EnhancedComparator<Person>() {
            @Override
            public int secondLevel(Person o1, Person o2) {
                int result = o1.lastName().compareTo(o2.lastName());
                return (result == 0 ? secondLevel(o1, o2) : result);
            }

            @Override
            public int compare(Person o1, Person o2) {
                return o1.firstName().compareTo(o2.firstName());
            }
        };
         people.sort(comparatorMixed);
         System.out.println(people);
         /*
         Now, let's say I want to turn this anonymous class, into a lambda expression? Well, I can't.
         This is because it's not technically a functional interface.
         When we say a functional interface can only have one abstract method, this means only one,
        and includes counting any inherited methods. Java can't determine which abstract method
          to use for this interface, so it can't be a target type for lambda expressions.
          */

    }
}
