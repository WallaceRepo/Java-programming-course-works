import java.util.Random;

public class enums {
    public static void main(String[] args) {
        WeekDays days = WeekDays.FRI;
        System.out.println(days);
        System.out.printf("Name is %s, Ordinal Value = %d%n", days.name(), days.ordinal());
//        FRI
//        Name is FRI, Ordinal Value = 5

        DayOfTheWeek myDay = DayOfTheWeek.TUES;
        System.out.println(myDay);
        for ( int i = 0; i < 10; i++) {
            myDay = getRandomDay();
            System.out.printf("Name is %s, Ordinal Value = %d%n", myDay.name(), myDay.ordinal());
            if (myDay == DayOfTheWeek.FRI){
                System.out.println("Found a Friday");
            }
        }
//        Name is TUES, Ordinal Value = 2
//        Name is MON, Ordinal Value = 1
//        Name is THURS, Ordinal Value = 4
//        Name is SUN, Ordinal Value = 0
//        Name is SUN, Ordinal Value = 0
//        Name is SAT, Ordinal Value = 6
//        Name is FRI, Ordinal Value = 5
//        Found a Friday
//        Name is WED, Ordinal Value = 3
//        Name is SUN, Ordinal Value = 0
//        Name is MON, Ordinal Value = 1
    }
     public static DayOfTheWeek getRandomDay(){
        int randomInteger = new Random().nextInt(7);
        var allDays = DayOfTheWeek.values();
        return allDays[randomInteger];
     }

}
 enum WeekDays {
    SUN, MON, TUES, WED, THURS, FRI, SAT
}
///// separate file
public enum DayOfTheWeek {
    SUN, MON, TUES, WED, THURS, FRI, SAT
}
