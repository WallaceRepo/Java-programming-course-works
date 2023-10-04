package util;

import model.Student;

import java.util.ArrayList;
import java.util.List;

public class QueryList < T extends Student & QueryItem> extends ArrayList <T> {
    // private List<T> items;
    public QueryList() {

    }
      public QueryList(List<T> items) {
          super(items);
        //  this.items = items;
      }
      //class's type parameter can't be used for a static method.
      //
      //The generic class's type parameter only has meaning for an instance,
      //
      //and therefore for an instance method. At the class level, this is unknown.
      //
      //When the generic class is loaded into memory, it's not loaded with any type parameter,
      //
      //so you can't use it in a static method, which is what I'm really trying to do here.
    //A generic method's type is unrelated to the type declared on the generic class.
    public QueryList<T> getMatches( String field, String value) {
        QueryList<T> matches = new QueryList<>();
        for ( var item: this) {
            if(item.matchFieldValue(field, value)) {
                matches.add(item);
            }
        }
        return matches;
    }
//      public List<T> getMatches(String field, String value) {
//          List<T> matches = new ArrayList<>();
//          for ( var item: items) {
//              if(item.matchFieldValue(field, value)) {
//                  matches.add(item);
//              }
//          }
//          return matches;
//      }
}
