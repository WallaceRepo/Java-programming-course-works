import model.LPAStudent;
import model.LPAStudentComparator;
import model.Student;
import util.QueryItem;
import util.QueryList;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        QueryList<LPAStudent> queryList = new QueryList<>();
        for ( int i = 0; i < 5; i++) {
            queryList.add( new LPAStudent());
        }

        System.out.println("ordered");
        queryList.sort(Comparator.naturalOrder());
        printList(queryList);
        /*
           ordered
            10000 Cathy M         C++             2022     50.2%
            10001 Bill K          Python          2019     33.0%
            10002 Sophia R        C++             2019     54.3%
            10003 John J          C++             2018     69.5%
            10004 Cathy M         Java            2018     35.3%
         */
        System.out.println("Matches");
        var matches = queryList.getMatches("PercentComplete", "50")
                .getMatches("Course", "Python");
        matches.sort( new LPAStudentComparator());
        printList(matches);
        /*
      Matches
           10001 Bill K          Python          2019     33.0%
         */
        System.out.println("Ordered");
        matches.sort(null);
        printList(matches);
        /*
        Ordered
            10001 Bill K          Python          2019     33.0%
         */
    }

    public static void printList(List<?> students) {
        for ( var student: students) {
            System.out.println(student);
        }
    }
}
