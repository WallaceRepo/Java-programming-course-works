public class Main extends Object {

    public static void main(String[] args) {
         String bulletIt = "Print a Bullet List: \n" +
                 "\u2022 First Point \n" +
                 "\u2022 Sub Point " +
                 "\u2022 First Point \n" +
                 "\t\t\u2022 Sub Point ";
         System.out.println(bulletIt);
         String textBlock = """
                 Print a Bulleted List:
                        \u2022 Flowers
                        \u2022 Flowers
                           \u2022 Sub Folder""";
         System.out.println(textBlock);

         int age = 35;
         System.out.printf("Your age is %d\n", age);
           // d - for decimal integer; short, int, long
        //  f - float
         int yearOfBirth = 2023 - age;
         System.out.printf("Age = %d, Birth year = %d%n", age, yearOfBirth);
         //Age = 35, Birth year = 1988
         System.out.printf("Your age is %f.2%n", (float)age);
         // Your age is 35.000000.2

        for ( int i = 1; i <= 100000; i *= 10){
            System.out.printf("Printing %6d %n", i);
        }
//        Printing      1
//        Printing     10
//        Printing    100
//        Printing   1000
//        Printing  10000
//        Printing 100000

        String formattedString = String.format("Your age is %d", age);
        System.out.println(formattedString);
        // Your age is 35

        formattedString = "Your age is %d".formatted(age);
        System.out.println(formattedString);
        // Your age is 35
    }

    // String has 60 methods;
    
}
