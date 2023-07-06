// My way of solution
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int[] arr = getIntegers();
        System.out.println(Arrays.toString(sortInteger(arr)));
    }
 
    private static int [] getIntegers( ) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of elements in the array: ");
        // Getting the user Input;
        int size = scanner.nextInt();

        System.out.println("Enter the elements of the array: ");
        int [] arr = new int [size];

        for ( int i = 0; i < size; i++) {
              System.out.print("Enter " + (i + 1) + ": ");
              while(true) {
                  try {
                      arr[i] = Integer.parseInt(scanner.next());
                      break;
                  } catch (NumberFormatException e){
                      System.out.println("Invalid input. Please enter a valid integer");
                      System.out.print("Element " + (i + 1) + ": ");
                  }
              }
            }
           System.out.println("Array entered by the user:");
         for ( int num : arr) {
             System.out.print(num + " ");
        }
         return arr;
    }

    private static int[] sortInteger(int[] array) {
         int[] sortedArray = Arrays.copyOf( array, array.length);
         boolean flag = true;
         int temp;
         while (flag) {
             flag = false;
             for ( int i = 0; i < sortedArray.length -1; i++) {
                 if( sortedArray[i] < sortedArray[i + 1]) {
                     temp = sortedArray[i];
                     sortedArray[i] = sortedArray[i+1];
                     sortedArray[i+1] = temp;
                     flag = true;
                 }
             }
         }
         return sortedArray;
    }
}


// Someone did to pass udemy ide ///////////////
import java.util.Arrays;
import java.util.Scanner;

public class SortedArray {
    // write code here
    // public static Scanner scanner = new Scanner(System.in);
 
    public static int[] getIntegers(int arraySize) {
        Scanner scanner = new Scanner(System.in);
        int[] theArray = new int[arraySize];
        for (int i = 0; i < theArray.length; i++) {
            System.out.print("Enter an integer for index " + i + " out of " + theArray.length + ": ");
            theArray[i] = scanner.nextInt();
        }
        return theArray;
    }
 
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("Element " + i + " contents " + array[i]);
        }
    }
 
    public static int[] sortIntegers(int[] array) {
 
        int temp;
        boolean flag = true;
 
        while (flag) {
            flag = false;
            for (int i = 0; i < (array.length - 1); i++) {
                if (array[i] < array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    flag = true;
                }
            }
        }
        return array;
    }
}
