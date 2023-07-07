import java.util.Arrays;

public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
        int [] arr = {2,3,4,5,6};
        reverse(arr);
    }
     // mutating code or it changes the array
    private static void reverse (int[] array) {
        int maxIndex= array.length-1;
        int halfLength = array.length / 2;

        for (int i = 0; i < halfLength; i++) {
           int temp = array[i];
           array[i] = array[maxIndex - i];
           array[maxIndex-i] = temp;
        }
        System.out.println(Arrays.toString(array));
    }
   private static int [ ] reverseCopy( int[] array){
        int [ ] reverseArray = new int[array.length];
        int maxIndex = array.length - 1;
        for ( int el: array) {
            reverseArray[maxIndex--] = el;
        }
        return reverseArray;
   }
}
