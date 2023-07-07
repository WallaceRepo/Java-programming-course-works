public class Main {
     // ... called variable arguments - varargs
    //
    public static void main(String... args) {
         System.out.println("hello world again");

         String[] splitStrings = "Hello World again".split(" ");
         printText(splitStrings);

         System.out.println("_".repeat(20));
         printText("Hello");

        System.out.println("_".repeat(20));
        printText("Hello", "World", "again");

        System.out.println("_".repeat(20));
        printText();

        // Below is anonymous String array initializer.


        String[] sArray = { "first", "second","third", "forth"};
        System.out.print(String.join(",", sArray));
    }

    private static void printText(String... textList) {

        for ( String t: textList) {
            System.out.println(t);
        }
    }
}
