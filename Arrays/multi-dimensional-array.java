import java.util.Arrays;

public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
         // lets work with arrays
        int[][] array2 = new int[4][4];
         // assigning the second array member an initialization;
        array2[1] = new int[] {10, 30, 40 };
        System.out.println(Arrays.deepToString(array2));
      // [[0, 0, 0, 0], [10, 30, 40], [0, 0, 0, 0], [0, 0, 0, 0]]

        // we can't do anonymous initialisation as one-dimension array does.

       //  array2[1] = { 10, 30, 40 }

        // Multi-dimensional array

        Object[] anyArray = new Object[3];
        System.out.println(Arrays.toString(anyArray));
        // [null, null, null]
        anyArray[0] = new String[] { "a", "b", "c"};
        System.out.println(Arrays.deepToString(anyArray));
        // [[a, b, c], null, null]

        anyArray[1] = new String[][]{
                {"1", "2"},
                {"3", "4", "5"},
                {"6", "7", "8", "9"}
        };
       System.out.println(Arrays.deepToString(anyArray));
      //  [[a, b, c], [[1, 2], [3, 4, 5], [6, 7, 8, 9]], null]

        anyArray[2] = new int[2][2][2];
        System.out.println(Arrays.deepToString(anyArray));
       // [[a, b, c], [[1, 2], [3, 4, 5], [6, 7, 8, 9]], [[[0, 0], [0, 0]], [[0, 0], [0, 0]]]]

          // let's print this array using for loop to see it better
        for ( Object element : anyArray) {  // Object as a type
            System.out.println("Element type = " + element.getClass().getSimpleName()); // prints class name of each array
            System.out.println("Element toString() = " + element); // prints toString representation of each element
             // deepToString takes an array, so we need to cast our element to an array of Object, before we pass it to this method.
            System.out.println(Arrays.deepToString((Object[])  element)); // each elements of this multi-dimensional array
        }
//        Element type = String[]
//        Element toString() = [Ljava.lang.String;@eed1f14
//        [a, b, c]
//        Element type = String[][]
//        Element toString() = [[Ljava.lang.String;@7229724f
//                [[1, 2], [3, 4, 5], [6, 7, 8, 9]]
//        Element type = int[][][]
//        Element toString() = [[[I@4eec7777
//                [[[0, 0], [0, 0]], [[0, 0], [0, 0]]]
    }
}
