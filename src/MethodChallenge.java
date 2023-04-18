public class MethodChallenge {
    public static void main(String[] args) {

      System.out.println(getDurationString(3600, 54));
        }
    public static String getDurationString (int seconds){
       int hour = 0;
       int minute = 0;

       if (seconds <= 0) {
            System.out.println("Invalid input, please enter valid number");
            return "Invalid";
       };
       if ( seconds < 60) {
           return (hour + "h " + minute + "m " + seconds + " s");
       } else if( seconds < 3600 && seconds > 60) {
           minute = seconds / 60;
           seconds = seconds % 60;
           return (hour + "h " + minute + "m " + seconds + " s");
       }
       hour = seconds / 3600;
       minute = (seconds - (hour * 3600)) / 60;
       seconds = (seconds - (hour * 3600)) % 60;

       return (hour + "h " + minute + "m " + seconds + " s");
    }
    public static String getDurationString (int minutes, int seconds) {
        if (minutes <= 0 || (seconds <= 0 || seconds >= 59)) {
            System.out.println("Invalid input, please enter valid number");
            return "Invalid";
        };
        int result = (60 * minutes) +  seconds;
        System.out.println(result);
        return getDurationString(result);
    }
}
