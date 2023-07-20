import java.util.LinkedList;
import java.util.List;

record Town(String name, int distance) {
    @Override
    public String toString() {
        return String.format("%s (%d)", name, distance);
    }
};
public class Iterator {
    public static void main(String[] args) {

        List<Town> bookList = new LinkedList<>();
        LinkedList <Town> flowers = new LinkedList<>();

        Town newyork = new Town("newyork", 17);

        addPlace(bookList, newyork);
        addPlace(bookList, new Town("Dallas", 43));
        addPlace(bookList, new Town("Huston", 45 ));
        addPlace(bookList, new Town("Tyler", 49 ));
        addPlace(bookList, new Town("Austin", 51 ));

        addPlace( bookList, new Town("Sydney", 0));

        flowers.addFirst(new Town("New-York", 23));
        addPlace(flowers, new Town("hawaii", 19));
        addPlace(flowers, new Town("San Antonio", 37));
        
        System.out.println(bookList);
      // [Sydney (0), newyork (17), Dallas (43), Huston (45), Tyler (49), Austin (51)]
        System.out.println(flowers);
      // [hawaii (19), New-York (23), San Antonio (37)]
    }
    private static void addPlace(List<Town> list, Town place){
        if(list.contains(place)){
            System.out.println("Found duplicates: " + place);
            return;
        }
        for (Town t : list) {
            if(t.name().equalsIgnoreCase(place.name())){
                System.out.println("Found duplicates: " + t);
                return;
            }
        }
        int matchedIndex = 0;
        for ( var listPlace : list) {
            if(place.distance() < listPlace.distance()) {
                list.add(matchedIndex, place);
                return;
            }
            matchedIndex++;
        }
        list.add(place);
    }
}

