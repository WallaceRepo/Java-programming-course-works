import java.util.ArrayList;
import java.util.Arrays;

record GroceryItem(String name, String type, int count){
     // for record, we have to call the generated default constructor first, with all args.
    public GroceryItem(String name) {
        this(name, "DAIRY", 1);
    }
    // own toString method to this record to show in printing.

    @Override
    public String toString(){
        return String.format("%d %s in %s", count, name.toUpperCase(), type);
    }
}

public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
         // record a new type of class from java 14
        // array of object
       Object[] groceryArray = new Object[3];
       groceryArray[0] = new GroceryItem("milk");
       groceryArray[1] = new GroceryItem("apples", "PRODUCE", 6);
       groceryArray[2] = "5 oranges";
      // System.out.println(Arrays.toString(groceryArray));
// [GroceryItem[name=milk, type=DAIRY, count=1], GroceryItem[name=apples, type=PRODUCE, count=6], 5 oranges]

        // improve above code

        GroceryItem[] groceryArrays = new GroceryItem[3];
        groceryArrays[0] = new GroceryItem("milk");
        groceryArrays[1] = new GroceryItem("apples", "PRODUCE", 6);
        //groceryArrays[2] = "5 oranges";
        groceryArrays[2] = new GroceryItem("oranges", "PRODUCE", 5);
        System.out.println(Arrays.toString(groceryArrays));
            // [1 MILK in DAIRY, 6 APPLES in PRODUCE, 5 ORANGES in PRODUCE]
        // If we dont specify a type, with an ArrayList, it's going to use the Object class by default
        // This is called the raw use of this type. And it results in same problem with GroceryArray.
        ArrayList objectList = new ArrayList();
        // Look, any object can be put into this ArrayList type, like below adding.
        objectList.add(new GroceryItem("Butter"));
        objectList.add("yogurt"); // adding String

        // We don't want anything, we only want GroceryItems type in the list,
        // So, how do we specify a type for an ArrayList?
        // let's add a new declaration
        // type is inside diamond operator
        // ArrayList<GroceryItem> groceryList = new ArrayList<GroceryItem>();
        ArrayList<GroceryItem> groceryList = new ArrayList<>();
        groceryList.add( new GroceryItem("Butter"));
        groceryList.add( new GroceryItem("milk"));
        groceryList.add( new GroceryItem("oranges", "PRODUCE", 5));
        groceryList.add( 0, new GroceryItem("apples", "PRODUCE", 6));
        groceryList.set(0,
                 new GroceryItem("apples", "PRODUCE", 6));
        groceryList.remove(2);
        System.out.println(groceryList);
//   [6 APPLES in PRODUCE, 1 BUTTER in DAIRY, 5 ORANGES in PRODUCE]

    }


}
/// List 
import java.util.ArrayList;
import java.util.List;

public class MoreList {
    public static void main(String[] args) {
        String[] items = { "apples", "bananas", "milk", "eggs"};

        // Below is not ArrayList but, String actually returns array
        List<String> list = List.of(items); // of() static method calls items. it transformed array of string to list of string
        System.out.println(list);
        // [apples, bananas, milk, eggs]

        System.out.println(list.getClass().getName());
        // java.util.ImmutableCollections$ListN
       // list.add("yogurt");
        // Immutable = fixed, unchanging
        ArrayList<String> groceries = new ArrayList<>(list);
        groceries.add("yogurt");
        System.out.println(groceries);

        ArrayList<String> nextList = new ArrayList<>(
                List.of("pickles", "mustard", "cheese" ));

        groceries.addAll(nextList);
        System.out.println(groceries);

        // [apples, bananas, milk, eggs, yogurt, pickles, mustard, cheese]
    }
}

