import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MoreList {
    public static void main(String[] args) {
            // Array vs ArrayList
         // Instantiating Arrays vs ArrayLists
        String[] array = new String[10];
        ArrayList<String> arrayList = new ArrayList<>();

        // instantiating Arrays vs Lists and ArrayLists
        String [] myArray = new String[] {"first", "second", " third"};
        String[] myArray1 = {"first", "second", "third"};
        String[] myArray2 = new String[3];
        myArray2[0] = "first";
        myArray2[1] = "second";
        myArray2[2] = "third";

        ArrayList<String> arrayList1 = new ArrayList<>(List.of("first", "second", "third")); // List.of is factory method

        // Accessing Array vs ArrayList
        // last element
        System.out.println(myArray1.length-1);
        System.out.println(arrayList1.size()-1);

        //  Retrieving number of elements;
        int elementCount = myArray1.length;
        int elementCount1 =  arrayList1.size();

        System.out.println(elementCount1);
        System.out.println(elementCount);

        // Setting ( assigning an element)
        myArray1[0] = "ten";
        arrayList1.set(0, "ten");

        // getting an element
        String element = myArray1[0];
        String element1 =  arrayList1.get(0);

        // Printing Array elements vs ArrayList elements

        System.out.println(Arrays.toString(myArray1)); // have to use toString built-in support
        System.out.println(arrayList1); // prints nested lists

        // getting a string representation for Multi-Dimensional Arrays vs ArrayLists
        // array
        String [][] array2d = {
                { "first", "second", "third" },
                {" fourth", "fifth"}
        };
        // printing array 2d
        System.out.println(Arrays.deepToString(array2d));

        // ArrayList
        ArrayList<ArrayList<String>> multiDList = new ArrayList<>();
        // printing ArrayList
        System.out.println(multiDList);

        // Finding an element in an Array or ArrayList
        int binarySearch( myArray1, element); // Array must be sorted
//
        // ArrayList search methods, -1 return means no match found
        boolean contains(element);
        boolean containsAll( list of elements);
        int indexOf(element);
        int lastIndexOf(element);

        // Sorting Array vs ArrayList

         String[] arrays = {"one", "two", "three"};
         Arrays.sort(arrays);

        // ArrayList sorting
        ArrayList<String> myarrayList = new ArrayList<>(List.of("one", "two"));
        myarrayList.sort(Comparator.naturalOrder());
        myarrayList.sort(Comparator.reverseOrder());

        // To Switch between Arrays vs ArrayList classes

        String[] originalArray = new String[]  {"one", "two", "three"};
        // arrayList created by below method is not resizable;
        // and any change will be reflected on original array
        var originalList = Arrays.asList(originalArray); // Returns ArrayList from source array

        //Example
        String[] origArray = new String[]  {"one", "two", "three"};
        var origList = Arrays.asList(origArray);

        origList.set(0, "thousand");
        System.out.println("ArrayList origList: " + origList);
        // ArrayList origList: [thousand, two, three]
        System.out.println("Array origArray " + Arrays.toString(origArray));
        // Array origArray [thousand, two, three]


        // Arrays are created right away and no backing array
        List<String> newList = Arrays.asList("Sunday", "Monday", "Tuesday");
        System.out.println(newList);
        // List.of method returns an immutable list;


        // Creating Special Kinds of Lists
        // Using Arrays.asList; returns List type that is not resizeable, but is mutable / changeable
        String[] days = new String[] {" Sunday"," Monday"};
        List<String > myList = Arrays.asList(days);

        // Using List.of; returns List type immutable / unchangeable
        String[] day = new String[] {" Sunday"," Monday"};
        List<String > listOne = List.of(day);

        // Creating Arrays from ArrayLists
        // Most common method to create an array from an ArrayList
        ArrayList<String> stringLists = new ArrayList<>(List.of("jan","feb", "march"));
        String[] stringArray =  stringLists.toArray(new String[0]);
    }}
  
