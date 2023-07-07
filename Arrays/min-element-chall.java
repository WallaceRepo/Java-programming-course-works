import java.util.Arrays;
import java.util.Scanner;

public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
       int[] returnedArray = readIntegers();
       System.out.println(Arrays.toString(returnedArray));

       int minVal = findMin(returnedArray);
       System.out.println("min " + minVal);

    }
    private static int[] readIntegers(){
        Scanner scanner = new Scanner(System.in);
         System.out.print("Enter a list of integers, separated by commas:");
         String input = scanner.nextLine();

         String[] splits = input.split(",");
         int[]  arr = new int[splits.length];

         for ( int i = 0; i < splits.length; i++ ) {
             arr[i] = Integer.parseInt(splits[i].trim());
         }

        return arr;
    }
    private static int findMin( int[] array) {
        int min = Integer.MAX_VALUE;
        for ( int el : array) {
            if ( el < min) {
                min = el;
            }
        }
        return min;
    }
}
