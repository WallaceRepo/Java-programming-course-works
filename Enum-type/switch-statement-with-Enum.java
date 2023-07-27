import java.util.Random;

public class enums {
    public static void main(String[] args) {
        DayOfTheWeek weekday;
        for ( int i = 0; i < 10; i++) {
            weekday = getRandomDay();
           switchDayOfWeek(weekday);
        }
    }
     public static DayOfTheWeek getRandomDay(){
        int randomInteger = new Random().nextInt(7);
        var allDays = DayOfTheWeek.values();
        return allDays[randomInteger];
     }

     public static void switchDayOfWeek(DayOfTheWeek weekDay) {
        int weekDayInteger = weekDay.ordinal() + 1;
      // we learnt that switch statement on a String, int, char, short, and byte
         // now, switch statement can be enum type.
        switch(weekDay) {
            case WED -> System.out.println("Wednesday " + weekDayInteger);
            case SAT -> System.out.println("Saturday is Day " + weekDayInteger);
            default -> System.out.println(weekDay.name().charAt(0) + weekDay.name().substring(1).toLowerCase() +"day is Day " + weekDayInteger);
        }
//         Sunday is Day 1
//         Saturday is Day 7
//         Sunday is Day 1
//         Saturday is Day 7
//         Sunday is Day 1
//         Saturday is Day 7
//         Sunday is Day 1
//         Tuesday is Day 3
//         Saturday is Day 7
//         Friday is Day 6
     }

}
