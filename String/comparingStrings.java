public class Main extends Object {

    public static void main(String[] args) {
        // length, charAt, indexOf, lastIndexOf, isEmpty, isBlank
        printInformation("Hello world");
        printInformation("");
        printInformation("\t  \n");

        String helloWorld = "Hello World";
        System.out.printf("index of r= %d %n", helloWorld.indexOf('r'));

        System.out.printf("index of World = %d %n", helloWorld.indexOf("World"));
        System.out.printf("index of l= %d %n", helloWorld.indexOf('l'));
        System.out.printf("index of l= %d %n", helloWorld.lastIndexOf('l'));

        System.out.printf("index of l= %d %n", helloWorld.indexOf('l', 3));
        System.out.printf("index of l= %d %n", helloWorld.lastIndexOf('l', 8));

        // String comparison: contentEquals, equals, equalsIgnoreCase
        // contains, endsWith, startsWith, regionMatches
        String lowers = "bigTurtle";
        String biggie = lowers.toLowerCase();

        if(lowers.equals(biggie)) {
            System.out.println("Values match exactly");
        }
        if(lowers.equalsIgnoreCase(biggie)) {
            System.out.println("values match ignoring case");
        }
        if(lowers.startsWith("big")) {
            System.out.println("String starts with big");
        }
        if(lowers.endsWith("Turtle")) {
            System.out.println("String starts with Turtle");
        }
        if(lowers.contains("big")) {
            System.out.println("String contains with big");
        }
        if(lowers.contentEquals("bigTurtle")) {
            System.out.println("String equals bigTurtle");
        }

        String myStr1 = "Hello";
        String myStr2 = "Hello";
        String myStr3 = "Another String";
        // Returns true because they are equal
        System.out.println(myStr1.equals(myStr2));
        System.out.println(myStr1.equals(myStr3)); // false
    }
        public static void printInformation(String string) {
            int length = string.length();
            System.out.printf("Length = %d %n", length);

            if(string.isEmpty()) {
                System.out.println("String is empty");
                return;
            }

            if(string.isBlank()) {
                System.out.println("String is Blank");
                return;
            }
            System.out.printf("First char = %c %n", string.charAt(0));

            System.out.printf("Last char = %c %n", string.charAt(length-1));
        }
}
