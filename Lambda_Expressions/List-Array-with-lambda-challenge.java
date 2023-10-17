package Lambda;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;
import java.util.function.*;

public class Main {
        // create new instance of Random class
      private static Random random = new Random();

    public static void main(String[] args) {
        // Using Arrays
       String[] name = {  "Anna", "Bob", "Ed", "Gary", "David"};
     //  Arrays.setAll(name, i -> name[i].toUpperCase());
   //    System.out.println(Arrays.toString(name));

       // Create List backed by Array
        // is to wrap it in a list, and use the list methods
        //  Remember that creating a list like this gives us the ability to treat an array like a list,
        //  except for those operations that change the size, like remove or add.

        List<String> backedByArray = Arrays.asList(name);
          backedByArray.replaceAll(s -> s.toUpperCase());
          // Second Challenge
        backedByArray.replaceAll(s ->  s + " " + getRandomChar('A', 'D') + ".");
        System.out.println("Here " + backedByArray);
        System.out.println(Arrays.toString(name)); // original array changed after list method done

        // 3rd mini challenge
      //  backedByArray.replaceAll(s-> s + " " + getReverse(s)); // [ANNA C. .C ANNA, BOB A. .A BOB, ED D. .D DE, GARY D. .D YRAG, DAVID B. .B DIVAD]
     //   System.out.println(backedByArray);

        backedByArray.replaceAll(s-> s + " " + getReverse(s.split(" ")[0]));
        System.out.println(backedByArray); // [ANNA A. ANNA, BOB C. BOB, ED C. DE, GARY A. YRAG, DAVID D. DIVAD]
        Arrays.asList(name).forEach( s -> System.out.println(s));
        /*
        ANNA C. ANNA
        BOB C. BOB
        ED D. DE
        GARY A. YRAG
        DAVID D. DIVAD

         */
        // Remove names that have the first name equal to the last
//        Now, a list backed by an array, will throw an exception if you try to remove elements with the removeIf statement.


 //       Using List
//        List <String> names = new ArrayList<>(List.of(
//                "Anna", "Bob","Mark", "Jenny", "Mary"
//        ));
        List<String> names = new ArrayList<>(List.of(name));
        names.replaceAll( s -> s.toUpperCase());
        System.out.println(names);

        /// Second Mini Challenge

        names.replaceAll(s -> s + getRandomChar('A', 'Y') + ".");
        System.out.println(names);


      // Last Challenge
        // remove names that have the first name equal to the last
        names.removeIf(s -> s.substring(0, s.indexOf(" ")).equals(s.substring(s.lastIndexOf(" ") + 1)));
        names.removeIf(s -> {
            String first = s.substring(0, s.indexOf(" "));
            String last = s.substring(s.lastIndexOf(" ") + 1);
            return first.equals(last);
        });
        names.forEach(s -> System.out.println(s));
    }
      public static char getRandomChar(char startChar, char endChar)  {
        return (char) random.nextInt((int) startChar, (int)endChar + 1);
      }
      private static String getReverse(String word) {
             return new StringBuilder(word).reverse().toString();
      }

}
