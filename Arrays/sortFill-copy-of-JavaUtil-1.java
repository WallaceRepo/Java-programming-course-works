import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
         
              int[] firstArray = getRandomArray(10);
              System.out.println(Arrays.toString(firstArray));
                // [74, 55, 46, 6, 17, 3, 93, 79, 47, 39]
              //  Sort changes array
              Arrays.sort(firstArray); // no return type, void
              System.out.println(Arrays.toString(firstArray));
              // [3, 6, 17, 39, 46, 47, 55, 74, 79, 93]
              // fill changes array
              int[] secondArray = new int[10];
              System.out.println(Arrays.toString(secondArray));
              // [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
              Arrays.fill(secondArray, 5);
              System.out.println(Arrays.toString(secondArray));
               // [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]
              // make copy of array
              int[] thirdArray = getRandomArray(10);
              System.out.println(Arrays.toString(thirdArray));
               // if array values are primitive, then values get copied
               // if array values are object then object references get copied
              int[] fourthArray = Arrays.copyOf(thirdArray, thirdArray.length);
              System.out.println(Arrays.toString(fourthArray));

              Arrays.sort(fourthArray);
              System.out.println(Arrays.toString(thirdArray));
              System.out.println(Arrays.toString(fourthArray));
//               [79, 84, 24, 56, 79, 46, 84, 49, 61, 29]
//               [24, 29, 46, 49, 56, 61, 79, 79, 84, 84]
              //
              int[] smallerArray = Arrays.copyOf(thirdArray, 5);
              System.out.println(Arrays.toString(smallerArray));
              //[79, 84, 24, 56, 79]

              int[] largerArray = Arrays.copyOf(thirdArray, 15);
              System.out.println(Arrays.toString(largerArray));
              // [79, 84, 24, 56, 79, 46, 84, 49, 61, 29, 0, 0, 0, 0, 0]
      }

     private static int [] getRandomArray( int len) {
         Random random = new Random();
         int[] newInt = new int[len];

         for (int i = 0; i < len; i++){
             newInt[i] = random.nextInt(100);
         }
         return newInt;
     }

}
