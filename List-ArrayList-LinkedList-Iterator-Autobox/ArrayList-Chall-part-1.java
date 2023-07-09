import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class MoreList {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

         boolean flag = true;
        ArrayList<String> groceries = new ArrayList<>();
         while(flag) {
             printActions();
             // Switch is enhanced switch which we don't need break statements.
             // The enhanced switch does not have the problem of fall through like traditional switch statement does.
             // so the flag isn't going to get set to false there.
             switch (Integer.parseInt(scanner.nextLine())) {
                 case 1 -> addItems(groceries);
                 case 2 -> removeItems(groceries);
                 default -> flag = false;

             }
             groceries.sort(Comparator.naturalOrder());
             System.out.println(groceries);
         }
    }
    private static void addItems(ArrayList<String> groceries) {
        System.out.println("Add item(s) [separate items by comma]:");
        String[] items = scanner.nextLine().split(",");
        // groceries.addAll(List.of(items)); // it duplicate items are added and it doesn't remove user added white space
        for ( String i: items) {
            String trimmed = i.trim(); // removes trailing white space if user included any when entering the list of items
            if (groceries.indexOf(trimmed) < 0 ) { // if element is already in the list we get -1
                // alternative; groceries.contains(trimmed)
                groceries.add(trimmed);
            }
        }
    }
    private static void removeItems(ArrayList<String> groceries) {
        System.out.println("Remove item(s) [separate items by comma]:");
        String[] items = scanner.nextLine().split(",");

        for (String i : items) {
            String trimmed = i.trim();
            groceries.remove(trimmed);
        }
    }
    private static void printActions(){
        String textBlock = """
                Available actions;
                0 - to shutdown
                1 - to add item(s) to list (comma delimited list)
                2 - to remove any items (comma delimited list)
                Enter a number for which action you want to do:""";
        System.out.println(textBlock + " ");
}

}
//    Available actions;
//        0 - to shutdown
//        1 - to add item(s) to list (comma delimited list)
//        2 - to remove any items (comma delimited list)
//        Enter a number for which action you want to do:
//        1
//        Add item(s) [separate items by comma]:
//        eggs, fish, flour
//        [eggs, fish, flour]
//        Available actions;
//        0 - to shutdown
//        1 - to add item(s) to list (comma delimited list)
//        2 - to remove any items (comma delimited list)
//        Enter a number for which action you want to do:
//        2
//        Remove item(s) [separate items by comma]:
//        fish, flour
//        [eggs]
//        Available actions;
//        0 - to shutdown
//        1 - to add item(s) to list (comma delimited list)
//        2 - to remove any items (comma delimited list)
//        Enter a number for which action you want to do:
