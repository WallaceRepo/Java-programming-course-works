import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
         
          // declare array, here don't describe size.
        int[] integArray;
        int jobsArray[];

        String[] nameList;
        String courseList[];

          // instantiating an Array or Array Creation
        int[] schoolArray = new int[10];  // array will have 10 elements

         // Object Creation
        StringBuilder db = new StringBuilder();

        // Size of the array is fixed once created
        int [] myArr = new int[10];

        myArr[5] = 50;

        double[] myDoubleArray = new double[10];
        myDoubleArray[2] = 4.7;
        System.out.println(myDoubleArray[2]);

        // The Array initializer

        int [] firstPos = new int[]{1,3,4,5,6};

         // Assigning Anonymous Array, only can be used in a declaration statement
        int [] secondPos = { 1,2,3,4,8}; // without new int[] it's called anonymous array

        String [] names = {"Andy", "Bob", "David", "Eve"};

        int[] worksArr = { 1,2,3,4,5,6,7,8,9};

        int arrLength = worksArr.length;

       // Declaring below results error;
//        int[] newArr;
//        newArr = { 4,5,6,7,8}

        for (int i = 0; i < worksArr.length; i++ ){
            System.out.println(worksArr[i]);
        }

       // default value for any numeric will be 0, class type will be null, boolean will be false

       // Enhanced for loop; it's kind of forEach JS loop, used for array and other type of collections

        for( int element : worksArr ){
            System.out.println(element);
        }

        System.out.println(worksArr); // Array is special java Class is called by String method. it spits hash code of array

        //java.util.Arrays provides class methods, not instance methods

        System.out.println(Arrays.toString(worksArr));

           // Array of Objects. Array can be treated like any other objects in java
        Object ObjVariable = worksArr;

        if(ObjVariable instanceof int []) {
            System.out.println("ObjVariable is really an int array");
        }

        Object [] objectArray = new Object[3];

        objectArray[0] = "Hello"; // String Literal
        objectArray[1] = new StringBuilder("World"); // String Builder that creating String literal "World"
        objectArray[2] = worksArr;

      }


}
