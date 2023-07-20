// whole app, with remaining task
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

record Town(String name, int distance) {
    @Override
    public String toString() {
        return String.format("%s (%d)", name, distance);
    }
};
public class Iterator {
    public static void main(String[] args) {

        List<Town> bookList = new LinkedList<>();
        LinkedList<Town> flowers = new LinkedList<>();

        Town newyork = new Town("newyork", 17);

        addPlace(bookList, newyork);
        addPlace(bookList, new Town("Dallas", 43));
        addPlace(bookList, new Town("Huston", 45));
        addPlace(bookList, new Town("Tyler", 49));
        addPlace(bookList, new Town("Austin", 51));

        addPlace(bookList, new Town("Sydney", 0));

        flowers.addFirst(new Town("New-York", 23));
        addPlace(flowers, new Town("hawaii", 19));
        addPlace(flowers, new Town("San Antonio", 37));

        System.out.println(bookList);
        System.out.println(flowers);

        /// Set up Scanner to get input from user
        var iterator = bookList.listIterator();
        Scanner scanner = new Scanner(System.in);
        boolean quitLoop = false;
        boolean forward = true;

        printMenu();

        while (!quitLoop) {
            if (!iterator.hasPrevious()) {
                System.out.println("Originating : " + iterator.next());
                forward = true;
            }
            if (!iterator.hasNext()) {
                System.out.println("Final : " + iterator.previous());
                forward = false;
            }
            System.out.println("Enter Value: ");
            String menuItem = scanner.nextLine().toUpperCase().substring(0, 1);

            switch (menuItem) {
                case "F":
                    System.out.println("User wants to move forward");
                    if (!forward) {
                        forward = true;
                        if (iterator.hasNext()) {
                            iterator.next(); //
                        }
                    }
                    if (iterator.hasNext()) {
                        System.out.println(iterator.next());
                    }
                    break;
                case "B":
                    System.out.println("User wants to move backward");
                    if (forward) {
                        forward = false;
                        if (iterator.hasPrevious()) {
                            iterator.previous(); //
                        }
                    }
                    if (iterator.hasPrevious()) {
                        System.out.println(iterator.previous());
                    }
                    break;
                case "L":
                    System.out.println(bookList);
                    break;
                case "M":
                    printMenu();
                    break;
                default:
                    quitLoop = true;
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                Available actions ( select word or letter):
                 (F)orward
                 (B)ackwards
                 (L)ist Places
                 (M)enu
                 (Q)uit
                 """);
    }

    ;

    private static void addPlace(List<Town> list, Town place) {
        if (list.contains(place)) {
            System.out.println("Found duplicates: " + place);
            return;
        }
        for (Town t : list) {
            if (t.name().equalsIgnoreCase(place.name())) {
                System.out.println("Found duplicates: " + t);
                return;
            }
        }
        int matchedIndex = 0;
        for (var listPlace : list) {
            if (place.distance() < listPlace.distance()) {
                list.add(matchedIndex, place);
                return;
            }
            matchedIndex++;
        }
        list.add(place);
    }
}
