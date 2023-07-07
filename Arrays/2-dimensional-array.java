import java.util.Arrays;

public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
        // array initializer formatted over multiple lines
        int[][] array = {
                {1, 2},
                {11, 22, 33},
                {21, 22, 23, 34}
        };
        // Array initializer declared on one line
        int[][] myArray = {{2, 3}, {2367, 23, 4}, {2, 4, 5}};

        /// below says; an array of 3 nested arrays, and each nested array will have 3 ints.
        int[][] arr = new int[3][3];

        // also We can initialize a 2 dimensional array, without specifying the size of the nested arrays.

        int[][] arrays = new int[3][]; // an array of 3 null elements;

        // There are many ways to declare a two-dimensional array; seen in below;
        int[][] mybooksArray;
        int[] mynotesArray[];


        // lets work with arrays
        int[][] array2 = new int[4][4];
        System.out.println(Arrays.toString(array2));
       // [[I@65ab7765, [I@1b28cdfa, [I@eed1f14, [I@7229724f]
        System.out.println("array2.length = " + array2.length);
             // array2.length = 4
      
        for (int[] outer : array2) {
            System.out.println(Arrays.toString(outer));
        }
//       [0, 0, 0, 0]
//       [0, 0, 0, 0]
//       [0, 0, 0, 0]
//       [0, 0, 0, 0]
      
        for (int i = 0; i < array2.length; i++) {
            var innerArray = array2[i];
            for (int j = 0; j < innerArray.length; j++) {
                System.out.println(array2[i][j] + " ");
            }
            System.out.println();
        }
// 0 
// 0 
// 0 
// 0 

// 0 
// 0 
// 0 
// 0 

// 0 
// 0 
// 0 
// 0 

// 0 
// 0 
// 0 
// 0 
        // let's use enhanced for loop
        // when we use the var keyword instead of type, java jvm can infer or figure out the type.
        for (var outer : array2) {
            for (var element : outer) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

// 0 0 0 0 
// 0 0 0 0 
// 0 0 0 0 
// 0 0 0 0 
        
    // Java has method to print nested arrays
      System.out.println(Arrays.deepToString(array2));
// [[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]

      // Let's assign values
        for (int i = 0; i < array2.length; i++) {
            var innerArray = array2[i];
            for (int j = 0; j < innerArray.length; j++) {

                array2[i][j] = (i * 10) + (j + 1);
                System.out.println(array2[i][j] + " ");
            }
            System.out.println();
        }
// 1 
// 2 
// 3 
// 4 

// 11 
// 12 
// 13 
// 14 

// 21 
// 22 
// 23 
// 24 

// 31 
// 32 
// 33 
// 34 

    }
}
