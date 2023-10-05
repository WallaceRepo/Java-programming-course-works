import java.util.*;


public class Main {
    public static void main(String[] args) {
        /// Execute PigLatin list
            System.out.println("With Pig Latin Names");
            addPigLatinName(storeEmployees);
            /*
            With Pig Latin Names
                Macys   10008 Tom      2020 omTay
                Target  10216 Bud      2018 udBay
                Target  10015 Meg      2019 egMay
                Walmart 10322 Marty    2016 artyMay
                Walmart 10515 Joe      2021 oeJay
             */
        }
               /// Nested Local Class
         /*
       ? is generics wild card, that specifies an upper bound, for this method parameter's type argument.
       This means this method will only accept a List containing Store Employees, or any subtype of Store Employee.
        */
        public static void addPigLatinName(List<? extends StoreEmployee> list) {
                String lastName = "Piggy";

                class DecoratedEmployee extends StoreEmployee implements Comparable<DecoratedEmployee> {
                    private String pigLatinName;
                    private Employee originalName;

                    public DecoratedEmployee(String pigLatinName, Employee originalName) {
                        this.pigLatinName = pigLatinName + " " + lastName;
                        this.originalName = originalName;
                    }

                    @Override
                    public String toString() {
                        return originalName.toString() + " " + pigLatinName;
                    }

                    @Override
                    public int compareTo(DecoratedEmployee o) {
                        return pigLatinName.compareTo(o.pigLatinName);
                    }
                }
             List<DecoratedEmployee> newList = new ArrayList<>(list.size());
             for (var employee : list ) {
                 String name = employee.getName();
                 String pigLatin = name.substring(1) + name.charAt(0) + "ay";
                 newList.add(new DecoratedEmployee(pigLatin, employee));
             }


             newList.sort(null);
             for (var demployee: newList) {
                // System.out.println(employee.toString());
                 System.out.println(demployee.originalName.getName() + " " + demployee.pigLatinName);
             }
        }

       /// Additional Local Types
    /*
     As of JDK 16, you can also create a local record, interface and enum type, in your method block.
      These are all implicitly static types, and therefore aren't inner classes, or types, but static nested types.
    The record was introduced in JDK16.Prior to that release, there was no support for a local interface or enum in a method block either.
     */


} 
     }
///////////
