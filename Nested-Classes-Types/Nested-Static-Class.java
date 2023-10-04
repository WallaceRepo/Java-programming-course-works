
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
    }
}
///////////////////////
import java.util.Comparator;

public class Employee {

    public static class EmployeeComparator <T extends Employee> implements Comparator<Employee> {
        private String sortType;

        public EmployeeComparator() {
            this("name");
        }

        public EmployeeComparator(String sortType) {
            this.sortType = sortType;
        }

        @Override
        public int compare(Employee o1, Employee o2) {
            //  return o1.yearStarted - o2.yearStarted;
           //  return o1.getName().compareTo(o2.getName());
            if(sortType.equalsIgnoreCase("yearStarted")){
                return o1.yearStarted - o2.yearStarted;
            }
            return o1.name.compareTo(o2.name);
        }
    }
    private int employeeId;
    private String name;
    private int yearStarted;

    public Employee() {
    }

    public Employee(int employeeId, String name, int yearStarted) {
        this.employeeId = employeeId;
        this.name = name;
        this.yearStarted = yearStarted;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "%d %-8s %d".formatted(employeeId, name, yearStarted);
    }

}

/////////////
import java.util.Comparator;

public class EmployeeComparator <T extends Employee> implements Comparator<Employee> {

    @Override
    public int compare(Employee o1, Employee o2) {
      //  return o1.yearStarted - o2.yearStarted;
        return o1.getName().compareTo(o2.getName());
    }
}
