package Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class Main {
    private static Random random = new Random();

    private record Person(String first){
        public String last(String s) {
            return first + " " + s.substring(0, s.indexOf(" "));
        }
    }
    public static void main(String[] args) {
        String[] names = { "Anna", "Bob", "Cameron", "Donald", "Eva", "Jane"};

        Person tim = new Person("Tim");

        List<UnaryOperator<String>> list = new ArrayList<>(List.of(
                String:: toUpperCase,
                s-> s += " " + getRandomCharacter('D', 'M') + ".",
                s-> s += " " + reverse(s, 0, s.indexOf(" ")),
                Main::reverse,
                String::new,
                s-> new String("Howdy  " + s),
                String::valueOf,
                tim::last,
                (new Person("Mary"))::last
        ));
        applyChanges(names, list);
/*


[ANNA, BOB, CAMERON, DONALD, EVA, JANE]
[ANNA M., BOB E., CAMERON M., DONALD K., EVA D., JANE K.]
[ANNA M. ANNA, BOB E. BOB, CAMERON M. NOREMAC, DONALD K. DLANOD, EVA D. AVE, JANE K. ENAJ]
[ANNA .M ANNA, BOB .E BOB, CAMERON .M NOREMAC, DONALD .K DLANOD, EVA .D AVE, JANE .K ENAJ]
[ANNA .M ANNA, BOB .E BOB, CAMERON .M NOREMAC, DONALD .K DLANOD, EVA .D AVE, JANE .K ENAJ]
[Howdy  ANNA .M ANNA, Howdy  BOB .E BOB, Howdy  CAMERON .M NOREMAC, Howdy  DONALD .K DLANOD, Howdy  EVA .D AVE, Howdy  JANE .K ENAJ]
[Howdy  ANNA .M ANNA, Howdy  BOB .E BOB, Howdy  CAMERON .M NOREMAC, Howdy  DONALD .K DLANOD, Howdy  EVA .D AVE, Howdy  JANE .K ENAJ]
[Tim Howdy, Tim Howdy, Tim Howdy, Tim Howdy, Tim Howdy, Tim Howdy]
[Mary Tim, Mary Tim, Mary Tim, Mary Tim, Mary Tim, Mary Tim]

Process finished with exit code 0

 */
    }

    private static void applyChanges(String[] names, List<UnaryOperator<String>> stringFunctions) {
        List<String> backedByArray = Arrays.asList(names);

        for ( var functions : stringFunctions ) {
            backedByArray.replaceAll(s -> s.transform(functions));
            System.out.println(Arrays.toString(names));
        }
    }

    private static char getRandomCharacter(char startChar, char endChar ) {
        return (char) random.nextInt((int) startChar, (int) endChar +1);
    }
    private static String reverse(String s) {
        return reverse(s, 0, s.length());
    }
    private static String reverse ( String s, int start, int end) {
        return new StringBuilder(s.substring(start, end)).reverse().toString();

    }

}
