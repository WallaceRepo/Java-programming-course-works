public class Main {

    public static void main(String[] args) {

        String birthDate = "21/11/1982";
        int startingIndex = birthDate.indexOf("1982");
        System.out.println("startingIndex = " + startingIndex);
        System.out.println("Birth year = " + birthDate.substring(startingIndex));

        System.out.println("Month = " + birthDate.substring(3, 5));

        String newDate = String.join("/", "25","11", "1982");

        System.out.println("newDate = " + newDate);

        newDate = "25" + "/" + "11" + "/" +  "23" + "/" + "1982";
        System.out.println("newDate= " + newDate );

        System.out.println(newDate.replace('/', '-'));
        System.out.println(newDate.replace("2", "00"));

        System.out.println(newDate.replaceFirst("/", "-"));
        System.out.println(newDate.replace("2", "---"));

        System.out.println("ABC\n".repeat(3).indent(8));
        System.out.println("-".repeat(20));

        System.out.println("    ABC\n".repeat(3).indent(-2));
        System.out.println("-".repeat(20));

    }
}
