import java.util.LinkedList;
import java.util.ListIterator;

public class MyLists {
    public static void main(String[] args) {

      //  LinkedList<String> placeToVisit = new LinkedList<>();
      // LinedList using var
        var placesToVisit = new LinkedList<String>();
        placesToVisit.add("Dallas");
        placesToVisit.add(0, "Vegas");
        System.out.println(placesToVisit);
 // LinkedList implements deck interface, we have several other functions, to add elements
        addMoreElements(placesToVisit);
        System.out.println(placesToVisit);

        // removeElements(placesToVisit);
        System.out.println(placesToVisit);

//        gettingElements(placesToVisit);
//
//        printItinerary(placesToVisit);
//
//        printItinerary2(placesToVisit);

          testIterator(placesToVisit);


    }
    private static void addMoreElements(LinkedList<String> list) {
        list.addFirst("New-york");
        list.addLast("Tokyo");
        // Queue methods
        list.offer("Huston"); // adds at the end of a list. makes it like a queue, to add on the end of queue
        list.offerLast("Austin"); // does same as offer()
        list.offerFirst("Atlanta");

        // Stack Methods
        list.push("Utah");
    }
    private static void removeElements(LinkedList<String> list) {
        list.remove(3);
        list.remove("Atlanta");

        // LinkedList has additional methods to remove element, that arent on ArrayList.

        System.out.println(list);

        String s1 = list.remove(); // removes 1st element;
        System.out.println(s1 + " was removed.");

        String s2 = list.removeFirst(); // removes 1st element;
        System.out.println(s2 + " was removed.");

        String s3 = list.removeLast(); // removes 1st element;
        System.out.println(s3 + " was removed.");

        // Additional Queue methods offerFirst, offerLast for adding an element,
        // Also poll, pollLast, pollFirst to remove an element.

        String p1 = list.poll(); // removes 1st element;
        System.out.println(p1 + " was removed.");

        String p2 = list.pollFirst(); // removes 1st element;
        System.out.println(p2 + " was removed.");

        String p3 = list.pollLast(); // removes 1st element;
        System.out.println(p3 + " was removed.");

        // Stack method to add element
        list.push("Sydney");
        list.push("Chicago");
        list.pop(); // removes 1st element;
    }
    ///// LinkedList-Part-2
    private static void gettingElements(LinkedList<String> list) {

        System.out.println("Retrieved element " + list.get(4));
        System.out.println("First element " + list.getFirst());
        System.out.println("Last element " + list.getLast());

        System.out.println("Position of element " + list.indexOf("Austin"));
        System.out.println("Position of element" + list.lastIndexOf("Tokyo"));

        // Queue Retrieving an element
        System.out.println("get element = " + list.element());
        // Stack retrieval methods
        System.out.println("Element from peek() = " + list.peekFirst());
        System.out.println("Element from peekFirst() = " + list.peekFirst());
        System.out.println("Element from peekLast() = " + list.peekLast());
    }
        /// Traversing Linked List
        public static void printItinerary(LinkedList<String> list) {
             System.out.println("Trip starts at " + list.getFirst());
             for ( int i = 1; i < list.size(); i++ ) {
                 System.out.println("--> From: " + list.get(i - 1) + " to " + list.get(i));
             }
             System.out.println("Trip ends at " + list.getLast());
    }
    public static void printItinerary2(LinkedList<String> list) {
        System.out.println("Trip starts at " + list.getFirst());
         String previousTown = list.getFirst();
         for (String town : list) {
             System.out.println("--> From: " + previousTown + " to " + town);
             previousTown = town;
         }
        System.out.println("Trip ends at " + list.getLast());
    }
    public static void printItinerary3(LinkedList<String> list) {
        System.out.println("Trip starts at " + list.getFirst());
        String previousTown = list.getFirst();

        ListIterator<String> iterator = list.listIterator(1);
        while (iterator.hasNext()){
            var town = iterator.next();
            System.out.println("--> From: " + previousTown + " to " + town);
            previousTown = town;
        }
        System.out.println("Trip ends at " + list.getLast());
    }
     // So far, we mainly used for loops to traverse through elements in an array or list.
    // Traditional for loops with index or enhanced for loop and a collection

     // LinkedList Iterator; java provides other means to traverse lists
    // 2 alternatives are the Iterator and ListIterator;


    private static  void testIterator( LinkedList<String> list) {
        var iterator = list.iterator();
        while ( iterator.hasNext()) {
            System.out.println( iterator.next());
         }
        System.out.println(list);
    }
      // use above to remove element from linkedList
    private static  void iteratorRemove( LinkedList<String> list) {
        var iterator = list.iterator();
        while ( iterator.hasNext()) {
            // System.out.println( iterator.next());
            if(iterator.next().equals("Tokyo")){
                iterator.remove();
             //   list.remove() // it will cause error
                // same error if I try to do similar in enhanced for loop as well.
                // Iterator provides safe way, so make sure to call remove on iterator object
                // Iterator is forward only, only supports the remove method.
                // ListIterator can go both forward and backward, also has remove, add, set methods
            }
        }
        System.out.println(list);
    }

}
