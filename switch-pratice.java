public class SwitchPractice {
    public static void main(String[] args) {

        System.out.println("Days in month " + getDaysInMonth(2, 2023));
        System.out.println(isLeapYear(2000));
    };

    public static boolean isLeapYear(int year) {
        // return (year < 1 || year > 9999) && ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
        if (year < 1 || year > 9999) return false;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) return true;

        return false;
    };
       public static boolean isLeapYearPrev(int year) {

            if (year < 1 || year > 9999) {
                return false;
            } else if (year % 400 == 0) {
                return true;
            } else if (year % 100 == 0) {
                return false;
            } else if (year % 4 == 0) {
                return true;
            } else {
                return false;
            }
        }

     /*Similar to combining nested if statement with AND && operator, you can combine if and else if statements using OR || operator.  This should make sense because you're executing one OR the other code block. */
    public static int getDaysInMonth(int month, int year) {
//        if(1>month || month>12)
//            return -1;
//        else if ( 1>year || year >9999)
//            return -1;
        // same as
        if (month < 1 || month > 12 || year < 1 || year > 9999) {
            return -1;
        } else {
            /*Instead of listing every single case individually, group them together since without break keyword, they fall though to the next case.*/
         /*Class and you want to call a method in that class, you simply make a static method call.*/
            switch (month) {
                case 4:
                case 6:
                case 9:
                case 11:
                    return 30;
                case 2:
                   // return isLeapYear(year) ? 29 : 28;
//                   boolean leapYear = SwitchPractice.isLeapYear(year); // static method call to get boolean assigned to leapYear
//                    return leapYear ? 29 : 28;
                    return SwitchPractice.isLeapYear(year) ? 29 : 28;
                default:
                    return 31;
            }
        }
     }
};
