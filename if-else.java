// Java Expression vs Statement

public class AreaCalculator {
     public static double area (double radius) {
         if (radius < 0) return -1.0;
         return radius * radius * Math.PI;
     }
    public static double area (double x, double y) {
        if (x < 0 || y < 0) return -1.0;
        return x * y;
    }
    public static void printYearsAndDays(long minutes) {
         if (minutes < 0) {
             System.out.println("Invalid Value");
             return; }
         long result = 365 * 24 * 60;
        System.out.println(minutes + " min = " + minutes / result + " y and " + (minutes % result)/(24*60) + " d" );
    }
    public static void printEqual(int a, int b, int c) {
            if ((a < 0) || (b < 0) || (c < 0 )) {
                System.out.println("Invalid Value");
            } else if ((a == b) && (b == c)) {
                System.out.println("All numbers are equal");
            } else if((a!= b) && (b != c) && (a!=c)) {
                System.out.println("All numbers are different");
            } else {
                System.out.println("Neither all are equal or different");
            }
        }
    public static boolean isCatPlaying(boolean summer, int temperature) {
         if(summer && (temperature >= 25 && temperature <= 45)) {
             return true;
         } else if (!summer && (temperature >= 25 && temperature <= 35)){
             return true;
         } else {
             return false;
         }
    }
}
