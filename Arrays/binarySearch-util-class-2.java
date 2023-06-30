import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Finding match using binary, testing equality of arrays
        // Interval searching
        // Static method, binarySearch is Array class.
        // Array has to be sorted.
        // Elements must be comparable or same type

        // if No match found then method returns -1

         String[] sArray = { "Able", "Jane", "Mark", "Ralph", "David"};
        Arrays.sort(sArray);
        System.out.println(Arrays.toString(sArray));
        if (Arrays.binarySearch(sArray, "Mark") >= 0) {
            System.out.println("Found Mark in the list");
        }

        //  to check if 2 arrays are equal
        // checks array's all members and its position to be same and array length to be same
        int[] s1 = { 1, 2, 3, 4, 5 };
        int[] s2 = { 1, 2, 3, 4, 5 };

        if( Arrays.equals(s1, s2)) {
            System.out.println("Arrays are equal");
        } else {
            System.out.println("Arrays are NOT equal");
        }
      }

      


}
