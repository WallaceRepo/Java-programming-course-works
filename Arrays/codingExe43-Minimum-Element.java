/* Minimum Element
Write a method called readInteger() that has no parameters and returns an int.
It needs to read in an integer from the user - this represents how many elements the user needs to enter.
Write another method called readElements() that has one parameter of type int
The method needs to read from the console until all the elements are entered, and then return an array containing the elements entered.
And finally, write a method called findMin() with one parameter of type int[]. The method needs to return the minimum value in the array.
The scenario is:

1. readInteger() is called.
2. The number returned by readInteger() is then used to call readElements().
3. The array returned from readElements() is used to call findMin().
4. findMin() returns the minimum number.

[Do not try and implement this. It is to give you an idea of how the methods will be used]
TIP: Assume that the user will only enter numbers, never letters.
TIP: Instantiate (create) the Scanner object inside the method. There are two scanner objects, one for each of the two methods that are reading in input from the user. */


import java.util.Arrays;
import java.util.Scanner;

public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
       int returnedSize = readInteger();
       System.out.println(returnedSize);

       int [] enteredArray = readElements(returnedSize);
       System.out.println(Arrays.toString(enteredArray));

       int minVal = findMin(enteredArray);
       System.out.println("min " + minVal);

    }
    private static int readInteger() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a number of elements or array length; ");
            try {
                String input = scanner.next();
                return (Integer.parseInt(input));
             } catch (NumberFormatException e) {
                System.out.print("Invalid input, please enter a valid integer ");
            }
        }
    }
    private static int[] readElements(int size) {

        int [] arr = new int [size];
        Scanner scanner = new Scanner(System.in);

           while(true) {
               System.out.print("Enter " + size + " comma-separated numbers of elements: ");
                  try {
                      String input = scanner.nextLine();
                      String [] splits = input.split(",");
                      if (splits.length == size) {
                          for (int i = 0; i < splits.length; i++) {
                              arr[i] = Integer.parseInt(splits[i].trim());
                          }
                          return arr;
                       } else {
                          System.out.println("Invalid input. Please enter " + size + " comma-separated numbers.");
                        }
                     } catch (NumberFormatException e){
                      System.out.println("invalid input. Please enter a valid integer");
                  }
             }
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
