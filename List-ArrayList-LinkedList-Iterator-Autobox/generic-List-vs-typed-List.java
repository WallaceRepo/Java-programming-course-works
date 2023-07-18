import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//public record GroceryItem(String name){
//
//
//
//};

public class MoreList {
    public static void main(String[] args) {
         // generic type list
        List julyList = new ArrayList();
        julyList.add("Assignment1");
        julyList.add("Assignment2");
        julyList.add("Assignment3");
        julyList.add(new Integer(3));
        System.out.println(julyList);

        julyList.remove(1);
        julyList.get(0);

        String firstObj = (String) julyList.get(0);

        Iterator iterator = julyList.iterator();

        while(iterator.hasNext()) {
            Object  next = iterator.next();
            System.out.println(next);
        }

        for ( Object next : julyList){
            System.out.println(next);
        }

        for (int i = 0; i < julyList.size(); i++){
            Object next = julyList.get(i);
            System.out.println(next);
        }

        julyList.clear();
        //
        List<String> myList = new ArrayList<String>();
         myList.add("Assignment1");
         myList.add("Assignment2");
         myList.add("Assignment3");
       //  myList.add(new Integer(3));   // not allowed type mismatch
        System.out.println(myList);

        String myString = myList.get(0);
        Iterator<String> iterator1 = myList.iterator();

        while(iterator.hasNext()){
            String next = iterator1.next();
        }

        for (String next : myList) {
            System.out.println(next);
        }

        for (int i = 0; i < myList.size(); i++){
            String next = myList.get(i);
            System.out.println(next);
        }

    }
    

}
