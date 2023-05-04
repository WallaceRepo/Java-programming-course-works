import java.util.Scanner;

public class ParsingValuesReadInput {
    public static void main(String[] args) {
        int currentYear = 2023;
        String userBdate = "1999";

        int dataOfBirth = Integer.parseInt((userBdate)); // Static method on Integer/ wrapper class, works only on classname calls

        String rainAmount = "22.5";
        double averRain = Double.parseDouble(rainAmount);
        try {
            System.out.println(getInputFromConsole(currentYear));
        } catch (NullPointerException e) {
            System.out.println(getInputFromScanner(currentYear));
        }

    }
    // Reading data from console or terminal
    //System.in, System.console, Command Line Argument, Scanner
     public static String getInputFromScanner(int currentYear){
        // For reading input from the console or terminal, create instance of Scanner class, pass System.in
         Scanner scanner = new Scanner(System.in);

         System.out.println("Hi, What a achieving day? Name ");
         String name = scanner.nextLine();
         System.out.println("hi " + name + " Yes, you will be hired for best salary soon");

         System.out.println("What your year born? ");
         String age = scanner.nextLine();
         int year = currentYear - Integer.parseInt(age);
         return "So you are " + year + " year old";

         // For reading input from a file, we insantiate a scanner object with new with Scanner class, pass File object
//         Scanner scfile = new Scanner(new File("fileName"));

     }
                // run using terminal
     public static  String getInputFromConsole(int currentYear){
         // .readline() supports user prompt;
        String name = System.console().readLine("Hi, A great choice for learning Java? Name ");
        System.out.println("hi " + name + " Yes, you will be hired for best salary soon");

        String dateB = System.console().readLine("What your year born? ");
        int age = currentYear - Integer.parseInt(dateB);
        return "So you are " + age + " year old";
    }


}

//////////// Added Loop 
import java.util.Scanner;

public class ParsingValuesReadInput {
    public static void main(String[] args) {
        int currentYear = 2023;
        String userBdate = "1999";

        int dataOfBirth = Integer.parseInt((userBdate)); // Static method on Integer/ wrapper class, works only on classname calls

        String rainAmount = "22.5";
        double averRain = Double.parseDouble(rainAmount);
        try {
            System.out.println(getInputFromConsole(currentYear));
        } catch (NullPointerException e) {
            System.out.println(getInputFromScanner(currentYear));
        }

    }
    // Reading data from console or terminal
    //System.in, System.console, Command Line Argument, Scanner
     public static String getInputFromScanner(int currentYear){
        // For reading input from the console or terminal, create instance of Scanner class, pass System.in
         Scanner scanner = new Scanner(System.in);

         System.out.println("Hi, What a achieving day? Name ");
         String name = scanner.nextLine();
         System.out.println("hi " + name + " Yes, you will be hired for best salary soon");

         boolean validDOB = false;
         int age = 0;

         do {
             System.out.println("Enter a year of birth > = " + (currentYear-125) + " and <= " + (currentYear));
             try {
                 age = CheckData(currentYear, scanner.nextLine());
                 validDOB =  age < 0 ? false : true;
             } catch (NumberFormatException badUserData){
                 System.out.println("Characters not allowed, Try again.");
             }
          } while ( !validDOB);

//         System.out.println("What your year born? ");
//         String age = scanner.nextLine();
//         int year = currentYear - Integer.parseInt(age);
           return "So you are " + age + " year old";

         // For reading input from a file, we insantiate a scanner object with new with Scanner class, pass File object
//         Scanner scfile = new Scanner(new File("fileName"));

     }
                // run using terminal
     public static  String getInputFromConsole(int currentYear){
         // .readline() supports user prompt;
        String name = System.console().readLine("Hi, A great choice for learning Java? Name ");
        System.out.println("hi " + name + " Yes, you will be hired for best salary soon");
        String dateB = System.console().readLine("What your year born? ");
         int age = currentYear - Integer.parseInt(dateB);
          return "So you are " + age + " year old";

    }

    public static int CheckData(int currentYear, String dateOfBirth){
        int dob = Integer.parseInt(dateOfBirth);
        int minimumYear = currentYear - 125;

        if((dob < minimumYear) || (dob > currentYear)) {
            return -1;
        }
        return  (currentYear - dob);
    }

}
