
import java.util.*;


public class Main {
    public static void main(String[] args) {
         List <Employee> employees = new ArrayList<>(List.of(
                 new Employee(10001, "Ralfph", 2015),
                 new Employee(10005, "Carole", 2021),
                 new Employee(10022, "Jane", 2012),
                 new Employee(13151, "Laura", 2020),
                 new Employee(10050, "Jim", 2018)
         ));
//         var comparator = new EmployeeComparator<>();
//         employees.sort(comparator);
        employees.sort(new Employee.EmployeeComparator<>("yearStarted").reversed());

         for ( Employee e : employees) {
             System.out.println(e);
         }
         System.out.println("Store Members");

         List< StoreEmployee> storeEmployees = new ArrayList<>(List.of (
                 new StoreEmployee(10015, "Meg", 2019, "Target"),
                 new StoreEmployee(10515, "Joe", 2021, "Walmart"),
                 new StoreEmployee(10008, "Tom", 2020, "Macys"),
                 new StoreEmployee(10216, "Bud", 2018, "Target"),
                 new StoreEmployee(10322, "Marty", 2016, "Walmart")
         ));

//To create an instance of an inner class, you first must have an instance of the Enclosing Class.
//From that instance you call .new, followed by the inner class name and the parentheses,
//taking any constructor arguments.
//        StoreEmployee genericEmployee = new StoreEmployee();
//        StoreEmployee.StoreComparator<StoreEmployee> innerClass = genericEmployee. new StoreComparator<>();

         var genericEmployee = new StoreEmployee();
         var comparator = genericEmployee. new StoreComparator<>();
         /* My store employees are sorted by store first, than year started,
            which is how I implemented the StoreComparator's compare method.
            If I didn't want to use a local variable, I can chain the instantiations.
            Let me show you that. */
        var comparator1 = new StoreEmployee().new StoreComparator<>();
         storeEmployees.sort(comparator1);

         for (StoreEmployee e: storeEmployees) {

             System.out.println(e);
             /*
                 10005 Carole   2021
                13151 Laura    2020
                10050 Jim      2018
                10001 Ralfph   2015
                10022 Jane     2012
                    Store Members
                    Macys   10008 Tom      2020
                    Target  10216 Bud      2018
                    Target  10015 Meg      2019
                    Walmart 10322 Marty    2016
                    Walmart 10515 Joe      2021
              */
         }
    }
}

