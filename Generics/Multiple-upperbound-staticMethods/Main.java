import util.LPAStudent;
import model.Student;
import util.QueryItem;
import util.QueryList;

import java.util.*;
record Employee(String name) implements QueryItem {
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}

public class Main {
    public static void main(String[] args) {

        int studentCount = 10;

        List<Student> students = new ArrayList<>();
        for ( int i = 0; i < studentCount; i++) {
            students.add(new Student());
        }
        printList(students);

        List<LPAStudent> lpaStudents = new ArrayList<>();
        for ( int i = 0; i < studentCount; i++) {
            lpaStudents.add(new LPAStudent());
        }
        printList(lpaStudents);

        testList(new ArrayList<String>(List.of("Abby", "barry", "lucy")));
        testList(new ArrayList<Integer>(List.of(1,3,2)));
        /*      String: ABBY
                String: BARRY
                String: LUCY
                Integer: 1.0
                Integer: 3.0
                Integer: 2.0


*/     // using var, which means the type should be inferred
        var queryList = new QueryList<>(lpaStudents);
        var matches = queryList.getMatches("Course", "Python");
        printList(matches);
        /*
        2022: Bill C          Python          2022     72.9%
        2018: Ann J           Python          2018     51.6%
        2020: Sophia R        Python          2020     24.8%
         */
        var students2021 = QueryList.getMatches(students, "YearStarted", "2021");
        printList(students2021);
        /*
        2021: John Y          Java            2021
        2021: John R          Python          2021
         */
    /*  When the type can't be inferred,
        we can specify the type argument before the method invocation, after the class name, and dot there.
        This is saying, the new list that will get created in the getMatches method,
        will be a list of Students now.
    */  var students2022 = QueryList.<Student>getMatches(new ArrayList<>(), "YearStarted", "2021");
        printList(students2022);

    //   QueryList<Employee> employeeQueryList = new QueryList<Employee>();


    }



    // Now I can only use this method for a List of Students, or a subtype of Students.
    //And now, because I've done that, I'm able to use Student methods within this method block.
    //I have one method, unique to the Student class,
    //and that was the student get year started method
    // generic method
    public static <T extends Student> void printList(List<T> students) {
        for ( var student : students) {
            System.out.println(student.getYearStarted() + ": " + student);
        }
        System.out.println();
    }
    // non-generic method
    //So what is this? This syntax is what Java calls a wildcard
    //in the type argument. A wildcard is represented by a question mark.
    public static void printMoreLists(List<? extends Student> students) {
        for ( var student : students) {
            System.out.println(student.getYearStarted() + ": " + student);
        }
        System.out.println();
    }
// A wildcard can only be used in a type argument, not in the type parameter declaration.
    // A wildcard means the type is unknown.
public static void testList(List<?> list) {
    for ( var element : list) {
        if(element instanceof String s) {
            System.out.println("String: " + s.toUpperCase());
        } else if(element instanceof Integer i) {
            System.out.println("Integer: " + i.floatValue());
        }

    }
}
//    public static void testList(List<String> list) {
//        for ( var element : list) {
//            System.out.println("String: " + element.toUpperCase());
//        }
//    }
//    public static void testList(List<Integer> list) {
//        for ( var element : list) {
//            System.out.println("Integer: " + element.floatValue());
//        }
//    }
/*
Cathy M         C++             2020
John J          Python          2019
Bill W          C++             2018
Bill X          Python          2020
Bill K          Python          2020
Cathy F         Java            2018
Bill M          Java            2021
Sophia W        Python          2020
Cathy S         Java            2019
Cathy U         Python          2022

Ann Z           Python          2021     36.8%
Bill S          Java            2022     67.1%
John J          Python          2019     50.3%
Cathy N         Java            2020     62.7%
Ann G           C++             2019     52.5%
Bill D          C++             2021      7.2%
John K          Java            2021     21.6%
Cathy N         Java            2022     34.2%
Ann Q           C++             2018     44.2%
Ann L           Java            2022     99.8%

 */
}
