import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Iterator {
    public static void main(String[] args) {

        List myList = new ArrayList();

        myList.add("Flowers");
        myList.add("Blue Jay");
        myList.add("Morning sun");

        var iterator = myList.iterator();
        while(iterator.hasNext()){
            String value = (String) iterator.next();
            if(value.equals("Fish")) {
                iterator.remove();
                myList.add("white petals");


            }
            System.out.println(value);
        }
         // once while loop done we can't use same iterator instance. So we instantiate new one.
        // Or we can loop from the end using hasPrevious(); in case of linkedList.
        List bookList = new LinkedList();
           bookList.add("tomatoes");
           bookList.add("carrots");
           bookList.add("seeds");

           var iterator1 = bookList.listIterator(); // listIterator() works with only LinkedList.
           System.out.println(bookList);

           while ( iterator1.hasNext()) {
               System.out.println(iterator1.next());
           }

           while (iterator1.hasPrevious()){
               System.out.println(iterator1.previous());
           }

    }

}
