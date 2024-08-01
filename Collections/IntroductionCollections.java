package Collections.Methods;

import java.util.*;

public class UnderstandingBigPicture {


    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        String[] names = {"Anna", "Bob", "Carol", "David", "Edna", "Cindy"};

        list.addAll(Arrays.asList(names));

        System.out.println(list);

                 // check if list contains
        System.out.println(list.contains("Bob"));
              // using lambda expression
        list.removeIf( s -> s.charAt(0) == 'C');
        System.out.println(Arrays.toString(names));
        System.out.println(list);


        /// Collection Interface or the Base interface for elements such as list and array
       System.out.println("==================");
        Collection<String> list1 = new ArrayList<>();
        String[] names1 = {"Anna", "Bob", "Carol", "David", "Edna", "Cindy"};

        list1.addAll(Arrays.asList(names1));

        System.out.println(list1);

        // check if list contains
        System.out.println(list1.contains("Bob"));
        // using lambda expression
        list1.removeIf( s -> s.charAt(0) == 'C');
        System.out.println(Arrays.toString(names1));
        System.out.println(list1);

        /// Let's assign TreeSet to Collection
        System.out.println("==================");

        Collection<String> list2 = new TreeSet<>();

        String[] names2 = {"Anna", "Bob", "Carol", "David", "Edna", "Cindy"};

        list2.addAll(Arrays.asList(names2));

        System.out.println(list2);

        // check if list contains
        System.out.println(list2.contains("Bob"));
        // using lambda expression
        list2.removeIf( s -> s.charAt(0) == 'C');
        System.out.println(Arrays.toString(names2));
        System.out.println(list2);

        /// Let's assign TreeSet to Collection
        System.out.println("==================");

        Collection<String> list3 = new HashSet<>();

        String[] names3 = {"Anna", "Bob", "Carol", "David", "Edna", "Cindy"};

        list3.addAll(Arrays.asList(names3));

        System.out.println(list3);

        // check if list contains
        System.out.println(list3.contains("Bob"));
        // using lambda expression
        list3.removeIf( s -> s.charAt(0) == 'C');
        System.out.println(Arrays.toString(names3));
        System.out.println(list3);
/*
The Collection interface doesn't have a sort method.
The List interface does, but the Collection interface is more abstracted than a list.
Another thing to know about the Collection interface is, that there aren't any direct implementations of this top level interface.
Other interfaces are derived from it, and implementations (or concrete classes) implement the derived interfaces, like List and Set.
 */
      //  list3.sort()
          
    }

}
