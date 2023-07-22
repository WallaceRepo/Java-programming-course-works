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
        

    }
    private static Double getDoubleObject(){
        return Double.valueOf(100); // static factory method for boxing/wrapping primitive to instanciate an object type.

    }
    private static double getLiteralDoublePrimitive(){
        return 100.0;
    }
}
