import java.util.ArrayList;
import java.util.Arrays;

public class AutoboxingUnboxing {

    public static void main(String[] args) {
         // Autoboxing and unboxing is simply instantiating wrapper class for primitive and wrapped primitive back to primitive type
        // It's created because of List,LinkedList, ArrayList do not support / use primitive types.
        // They use only object types.
         Integer boxedInt = Integer.valueOf(18); // preferred but unnecessary
         Integer deprecatedBoxingInt = new Integer(18); // Deprecated way since JDK 9
        int unboxedInt = boxedInt.intValue(); // unnecessary unboxing


        //Automatic boxing / unboxing

        Integer autoBoxing = 15;
        int autoUnBoxed = autoBoxing;

        System.out.println(autoBoxing.getClass().getName());
       //  System.out.println(autoUnBoxed.getClass().getName());

        Double resultBoxed = getLiteralDoublePrimitive();
        double resultUnboxed = getDoubleObject();

        // Arrays and Lists in use
        Integer[] wrapperArray = new Integer[5];
        wrapperArray[0] = 50;
        System.out.println(Arrays.toString(wrapperArray));
      // [50, null, null, null, null]
        System.out.println(wrapperArray[0].getClass().getName());
        // java.lang.Integer

        Character[] characterArray = { 'a','b','c','d'};
        System.out.println(Arrays.toString(characterArray));
        // [a, b, c, d]

       // var ourList = List.of(1,2,3,4,5);

        var ourList = getList(1,2,3,4,5);
        System.out.println(ourList);
    }
    private static ArrayList<Integer> getList(int... varargs) {
        ArrayList<Integer> aList = new ArrayList<>();
        for( int i : varargs) {
            aList.add(i);
        }
        return aList;
    }
    private static int returnInt (Integer i){
        return i;
    }
    private static Integer returnInteger( int i){
        return i;
    }
    private static Double getDoubleObject(){
        return Double.valueOf(100); // static factory method for boxing/wrapping primitive to instanciate an object type.

    }
    private static double getLiteralDoublePrimitive(){
        return 100.0;
    }
}
