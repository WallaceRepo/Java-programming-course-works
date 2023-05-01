//  method overload in java
public class FirstClass {
    private static int bonus;

    public static void main(String[] args) {
        boolean gameOver = true;
        int score = 800;
        int levelCompleted = 5;
        int bonus = 100;

        int finalScore = score;

        calc (gameOver, score, levelCompleted, bonus);

        calc(true, 800, levelCompleted, bonus);

        calc(true, 10000, 8, 200);

        int highScore = calculate(gameOver, score, levelCompleted, bonus);
    }

    public static void calc(boolean gameOver, int score, int levelCompleted, int bonus) {
         int finalScore = score;
         if(gameOver) {
            finalScore += (levelCompleted * bonus);
            finalScore += 1000;
            System.out.println("Your final score was " +finalScore);
        }
    }
    public static int calculate(boolean gameOver, int score, int levelCompleted, int bonus) {
        int finalScore = score;
           if(gameOver) {
            finalScore += (levelCompleted * bonus);
            finalScore += 1000;
            System.out.println("Your final score was " +finalScore);
          }
        return finalScore;
    }
}
/////////////
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



//////////////////////////
public class MethodChallenge {
    public static void main(String[] args) {

      System.out.println(getDurationString(-3945));
      System.out.println(getDurationString(-65, 45));
      System.out.println(getDurationString(65, 145));
        }
    public static String getDurationString (int seconds){
        if(seconds < 0) return  "Invalid data for seconds(" + seconds + "), must be a positive integer value";
       int minutes = seconds / 60;
       return getDurationString(seconds / 60, seconds % 60);
    }
    public static String getDurationString (int minutes, int seconds) {
        if(minutes < 0) return  "Invalid data for minutes(" + minutes + "), must be a positive integer value";
        if(seconds <= 0 || seconds >=59) return  "Invalid data for seconds(" + seconds + "), must be between 0 and 59";
        int hours = minutes / 60;
        int remainingMin = minutes % 60;
        return hours + "h" +remainingMin + "m " + seconds + "s";
    }
}
