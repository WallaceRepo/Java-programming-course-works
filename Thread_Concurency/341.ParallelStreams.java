import java.util.Arrays;
import java.util.Random;

public class parallel {
    public static void main(String[] args) {
          int numberLength = 100_000_000;
          long [ ] numbers = new Random().longs(numberLength, 1, numberLength).toArray();

          long start = System.nanoTime();
          double averageSerial = Arrays.stream(numbers).average().orElseThrow();
          long elapsedSerial = System.nanoTime() - start;
          System.out.printf("Ave = %.2f , elapsed = %d nanos or %.2f ms%n", averageSerial, elapsedSerial, elapsedSerial/1000000.0);

        start = System.nanoTime();
        double averageParallel = Arrays.stream(numbers).parallel().average().orElseThrow();
        long elapsedParallel= System.nanoTime() - start;
        System.out.printf("Ave = %.2f , elapsed = %d nanos or %.2f ms%n", averageParallel, elapsedParallel, elapsedParallel/1000000.0);

        // To see difference in parallel and serial execution
        long delta = 0;
        int iterations = 25;

        for (int i = 0; i < iterations; i++ ) {
            long start1 = System.nanoTime();
            double averageSerial2 = Arrays.stream(numbers).average().orElseThrow();
            long elapsedSerial2 = System.nanoTime() - start1;

            start1 = System.nanoTime();
            double averageParallel2 = Arrays.stream(numbers).parallel().average().orElseThrow();
            long elapsedParallel2= System.nanoTime() - start1;

            delta += ( elapsedSerial2 - elapsedParallel2);
        }
        System.out.printf("Parallel is [%d] nanos or [%.2f] ms faster on average %n", delta / iterations, delta / 1000000.0 / iterations);
    }

}

//        Ave = 50003151.31 , elapsed = 79502300 nanos or 79.50 ms
//        Ave = 50003151.31 , elapsed = 52798000 nanos or 52.80 ms
//        Parallel is [37525756] nanos or [37.53] ms faster on average
