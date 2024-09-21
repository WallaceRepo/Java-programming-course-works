package SchedulingTasks;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Person(String firstName, String lastName, int age ) {
    private final static String[] firsts = { "Able", "Bob", "Charlie", "Donna", "Eve" , "Fred"};
    private final static String[] lasts = {"Norton", "Peterson", "Quincy", "Richardson", "Smith"};
    private  final static Random random = new Random();
    public Person() {
        this( firsts [ random.nextInt(firsts.length)],
               lasts [ random.nextInt(lasts.length)],
               random.nextInt(18, 100));
    }
    public String toString() {
        return "%s, %s, (%d)".formatted(lastName, firstName, age);

    }

}
public class Main {
    public static void main(String[] args) {
        Stream.generate(Person::new)
                .limit(10)
                .parallel()
                .sorted(Comparator.comparing(Person::lastName))
                .forEach(System.out::println);

        var persons = Stream.generate(Person::new)
                .limit(10)
                .sorted(Comparator.comparing(Person::lastName))
                .toArray();

        for ( var person : persons ) {
            System.out.println(person);
        }
        System.out.println(" ------------------------------");
        Arrays.stream(persons)
                .limit(10)
                .parallel()
                .forEachOrdered(System.out::println);


        // Reduction on the parallel stream
        int sum = IntStream.range(1, 101)
                .parallel()
                .reduce(0, Integer::sum);
        System.out.println("The sum of the numbers is: " + sum);

        String humptyDumpty = """
                Humpty Dumpty sat on a wall,
                Humpty Dumpty had a great fall.
                All the king's horses and all the king's men
                couldn't put Humpty together again.
                """;
        System.out.println("-------------------------------");
        var words = new Scanner(humptyDumpty).tokens().toList();
        words.forEach(System.out::println);
        System.out.println("---------------------------");

        var backTogetherAgain = words
                .parallelStream()
                        .reduce("", (s1, s2) -> s1.concat(s2).concat(" "));
              //  .collect(Collectors.joining(" "));
        System.out.println(backTogetherAgain);

        Map<String, Long> lastNameCounts = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .collect(Collectors.groupingBy(Person::lastName, Collectors.counting()));
        lastNameCounts.entrySet().forEach(System.out::println);

        long total = 0;
        for ( long count : lastNameCounts.values()) {
            total += count;
        }
        System.out.println("Total = " + total);
    }
}

/*
Peterson, Bob, (25)
Norton, Charlie, (88)
Quincy, Donna, (57)
Norton, Able, (22)
Norton, Fred, (75)
Norton, Able, (48)
Smith, Fred, (84)
Quincy, Fred, (76)
Richardson, Charlie, (52)
Quincy, Fred, (83)
Norton, Able, (73)
Norton, Bob, (51)
Peterson, Able, (91)
Peterson, Fred, (79)
Quincy, Fred, (62)
Quincy, Charlie, (54)
Richardson, Eve, (38)
Richardson, Eve, (86)
Smith, Able, (32)
Smith, Able, (61)
 ------------------------------
Norton, Able, (73)
Norton, Bob, (51)
Peterson, Able, (91)
Peterson, Fred, (79)
Quincy, Fred, (62)
Quincy, Charlie, (54)
Richardson, Eve, (38)
Richardson, Eve, (86)
Smith, Able, (32)
Smith, Able, (61)
The sum of the numbers is: 5050
-------------------------------
Humpty
Dumpty
sat
on
a
wall,
Humpty
Dumpty
had
a
great
fall.
All
the
king's
horses
and
all
the
king's
men
couldn't
put
Humpty
together
again.
---------------------------
Humpty Dumpty sat   on a wall,    Humpty Dumpty had   a great  fall. All     the king's horses   and all the    king's men couldn't   put Humpty  together again.
 Richardson=2066
Quincy=1972
Smith=1968
Norton=1986
Peterson=2008
Total = 10000

 */
