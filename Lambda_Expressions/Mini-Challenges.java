package Lambda;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;
import java.util.function.*;

public class Main {
    public static void main(String[] args) {

        //  This is a Anonymous Class we want to change to a Lambda
        Consumer<String> printWords = new Consumer<String>() {
            @Override
            public void accept(String sentence) {
                String[] parts = sentence.split(" ");
                for (String part : parts) {
                    System.out.println(part);
                }
            }
        };
        // Lambda
        Consumer<String> printWordsLambda = sentence -> {
            String[] parts = sentence.split(" ");
            for (String part : parts) {
                System.out.println(part);
            }
        };
        printWords.accept("Let's split this up into an array");
        printWordsLambda.accept("Let's split this up into an array");

        Consumer<String> printWordsForeach = sentence -> {
            String[] parts = sentence.split(" ");
            Arrays.asList(parts).forEach(s -> System.out.println(s));
        };
        printWordsForeach.accept("Let's split this up into an array");


        Consumer<String> printWordsConcise = sentence -> {
            Arrays.asList(sentence.split(" ")).forEach(s -> System.out.println(s));
        };
        printWordsConcise.accept("Let's split this up into an array");

////////// Mini Challenge 2
        UnaryOperator<String> everySecondChars = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if (i % 2 == 1) {
                    returnVal.append(source.charAt(i));
                }
            }
            return returnVal.toString();
        };

        Function<String, String> everySecondCharss = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if (i % 2 == 1) {
                    returnVal.append(source.charAt(i));
                }
            }
            return returnVal.toString();
        };
        // Mini Challenge 3
        System.out.println(everySecondCharss.apply("1234567890"));


        ////// Mini Challenge 5; Call the method challenge 4 and pass "1234567890"
        System.out.println(everySecondCharacter(everySecondCharss, "1234567890"));


        /// Mini Challenge 6
        // Write Lambda expression declared with the Supplier Interface, it should return the String
// "I love Java", and assign it to a variable called, iLoveJava.
        Supplier<String> iLoveJava = ()-> "I love Java";
        Supplier<String> iLoveJava2 = () -> { return "I love Java";};

        // Mini Challenge 7
        String supplierResult =  iLoveJava.get();
        System.out.println(iLoveJava2.get());

    }
///////////////////// End of main method
    public static String everySecondChar(String source) {
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (i % 2 == 1) {
                returnVal.append(source.charAt(i));
            }
        }
        return returnVal.toString();
    }

    //////// Mini Challenge 4: Lambda method expression
    public static String everySecondCharacter( Function<String, String> func, String source) {
        return func.apply(source);
    }
}
