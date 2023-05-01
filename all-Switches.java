public class allSwitches {

    public static void main(String[] args) {
        int switchVal = 3;
        switch (switchVal) {
            case 1:
                System.out.println("Value was 1");
                break;
            case 2:
                System.out.println("Value was 2");
                break;
            case 3: case 4: case 5:
                System.out.println("Value was 3, a 4 or 4");
            default:
                System.out.println(("Was not 1 or 2"));
            break;
        }
        /// New addition "Enhanced switch statement

        int Val = 3;
        switch (Val) {
            case 1 -> System.out.println("Value was 1");
            case 2 -> System.out.println("Value was 2");
            case 3, 4, 5 -> {
                System.out.println("Value was 3, a 4 or 4");
            }
            default -> System.out.println(("Was not 1 or 2"));
        }
        String month = "hsdjkf";
        System.out.println((month + " is  in the " + aboveSame(month) + "quarter"));

      /*  printDayofWeek(0);
        printDayofWeek(8);
        printDayofWeek(4);*/
        System.out.println("Days in month " + getDaysInMonth(4,2023));
        System.out.println(isLeapYear(2020));
    }
    // double, float and long unsupported for switch variable
    public static String getQuarter(String month){
         switch (month){
             case "January":
             case "February":
             case "March":
                 return "1st";
             case "April":
             case "May":
             case "June":
                 return "2st";
             case "July":
             case "August":
             case "September":
                 return "3st";
             case "October":
             case "November":
             case "December":
                 return "4st";
         }
         return "bad";
    };
    // using enhanced switch
    public static String aboveSame(String month){
        return switch (month) {
            case "January", "February", "March" -> { yield "1st";}
            case "April", "May", "June" -> "2st";
            case "July", "August", "September" -> "3st";
            case "October", "November", "December" -> "4st";
            default -> {
                String badResponse = month + " is bad ";
                yield badResponse;
            }
        };
    };

    public static String charFind(char letter) {
        return switch (letter) {
            case 'A' -> {
                yield "Able";
            }
            case 'B' -> "Baker";
            case 'C' -> "Charlie";
            case 'D' -> "Dog";
            case 'E' -> "Easy";
            default -> {
                String badResponse = letter + " not found ";
                yield badResponse;
                }
            };
    };
    public static void printDayofWeek(int day){
        String dayofWeek = switch (day){
            case 0 -> { yield "Sunday"; }
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saterday";
            default -> "Inlavid Day";
        };
        System.out.println(day + " stands for " + dayofWeek);
      }
    public static void weekDay(int day) {
        String dayWeek = "Invalid Day";
        if (day == 0) {
            dayWeek = "Sunday";
        } else if (day == 1) {
            dayWeek = "Monday";
        } else {
            dayWeek = " Other days";
        }
        System.out.println(day + " stands for " + dayWeek);
    }

    public static void printNumberInWord (int num) {
        String result = switch (num) {
            case 0 -> "ZERO";
            case 1 -> "ONE";
            case 2 -> "TWO";
            case 3 -> "THREE";
            case 4 -> "FOUR";
            case 5 -> "FIVE";
            case 6 -> "SIX";
            case 7 -> "SEVEN";
            case 8 -> "EIGHT";
            case 9 -> "NINE";

            default -> "OTHER";
        };
        System.out.println(result);
    };
    public static boolean isLeapYear(int year) {

        if (year < 1 || year > 9999) return false;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) return true;

        return false;
    };

    public static int getDaysInMonthEnhanced(int month, int year){
        if (year < 1 || year > 9999) return -1;

        int days = switch(month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: yield 31;
            case 4: case 6: case 9: case 11: { yield  30; }
            case 2 : {
                if (isLeapYear(year)){ yield 29;}
                else {
                    yield 28; }
            }
            default: { yield -1; }
        };
        return days;
    }
    public static int getDaysInMonth(int month, int year){
        if (year < 1 || year > 9999) return -1;
        int days = 0;
         switch(month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                 days = 31;
                 break;
            case 4: case 6: case 9: case 11:
                days = 30;
                break;
            case 2 : {
                if (isLeapYear(year)) days = 29;
                else days = 28;
                break;
            }
            default: days = -1;
            break;
        };
        return days;
    }
};
