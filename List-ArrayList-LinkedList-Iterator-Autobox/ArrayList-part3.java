import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

        /// ArrayList search,
        System.out.println("Third item = " + groceries.get(2));
        // Third item = milk

        if ( groceries.contains("mustard")){
            System.out.println("List contains mustard");
            // List contains mustard
        }
        groceries.add("yogurt");
        System.out.println("First = " + groceries.indexOf("yogurt"));
       //  First = 4
        System.out.println("last = " +groceries.lastIndexOf("yogurt"));
        // last = 8
        System.out.println(groceries);

        groceries.remove(1);
        System.out.println(groceries);

        groceries.remove(1);
        System.out.println(groceries);

        groceries.removeAll(List.of("apples","eggs"));
        System.out.println(groceries);

        groceries.retainAll(List.of("apples","milk", "mustard")); // removes the items not in the both groceries and List.of items

        System.out.println(groceries);

      // My examples
        ArrayList<String> bugs = new ArrayList<>(List.of("buttefly", "bugs", "spider"));
        bugs.addAll(List.of("bugs", "spider"));
        System.out.println(bugs);
        //  [buttefly, bugs, spider, bugs, spider]
        System.out.println(bugs.lastIndexOf("bugs"));
        // 3
        bugs.retainAll(List.of("spider"));
        System.out.println(bugs);
        // [spider, spider]

        //  .clear() removes all elements from list

        bugs.clear();
        System.out.println(bugs);
        // []
        System.out.println("isEmpty = " + bugs.isEmpty());
        // isEmpty = true

        bugs.addAll(List.of("ants", "moths"));
        bugs.addAll(Arrays.asList("bird", "worms"));

        System.out.println(bugs);
        // [ants, moths, bird, worms]
        bugs.sort(Comparator.naturalOrder());
        System.out.println(bugs);
         // [ants, bird, moths, worms]
        bugs.sort(Comparator.reverseOrder());
        System.out.println(bugs);
        // [worms, moths, bird, ants]

        var bugsArray = bugs.toArray( new String[bugs.size()]);
        System.out.println(Arrays.toString(bugsArray));
        // [worms, moths, bird, ants]
        
    }
}
