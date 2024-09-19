package ParallelProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
class RecursiveSumTask extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    private final int division;

    public RecursiveSumTask(long[] numbers, int start, int end, int division) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.division = division;
    }

    protected Long compute() {
        if ((end - start) <= (numbers.length / division)) {
            System.out.println(start + " : " + end);
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            RecursiveSumTask leftTask = new RecursiveSumTask(numbers, start, mid, division);
            RecursiveSumTask rightTask = new RecursiveSumTask(numbers, mid, end, division);
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();
        }
    }
}
public class Main {
    public static void main(String[] args) throws Exception {
        int numbersLength = 100_000;
        long[] numbers = new Random().longs(numbersLength, 1, numbersLength).toArray();

        long sum = Arrays.stream(numbers).sum();
        System.out.println("sum = " + sum);

      //  ExecutorService threadPool = Executors.newWorkStealingPool(4);
        // Using ForkJoin
       //  ForkJoinPool threadPool = ( ForkJoinPool ) Executors.newWorkStealingPool(4);
        ForkJoinPool threadPool = ForkJoinPool.commonPool();
        // split a large task (summing an array) into smaller subtasks and distribute them across multiple threads using a WorkStealingPool
        List<Callable<Long>> tasks = new ArrayList<>();
        int taskNumbers = 10;
        int splitCount = numbersLength / taskNumbers;

        // create tasks
        for (int i = 0; i < taskNumbers; i++) {
            int start = i * splitCount;
            int end = start + splitCount;
            tasks.add(() -> {
                long tasksum = 0;
                for (int j = start; j < end; j++) {
                    tasksum += (long) numbers[j];
                }
                return tasksum;
            });

// Execute tasks and gather results
            List<Future<Long>> results = threadPool.invokeAll(tasks);
      // using above ForkJoinClass, lets print some of task's methods
            System.out.println("Parallelism = " + threadPool.getParallelism());
            System.out.println("Pool size = " + threadPool.getPoolSize());
            System.out.println("Steal count = " + threadPool.getStealCount());
            long totalSum = 0;
            for (Future<Long> result : results) {
                totalSum += result.get();
            }

            System.out.println("Total sum, threadPool  = " + totalSum);

            RecursiveTask<Long> task = new RecursiveSumTask( numbers, 0, numbersLength, 8);
            long forkJoinSum = threadPool.invoke(task);
            System.out.println(" RecursiveTask sum is: " + forkJoinSum);
            threadPool.shutdown();
            System.out.println(threadPool.getClass().getName());
        }
    }
}
//Parallelism = 15
//        Pool size = 15
//        Steal count = 124
//        Total sum, threadPool  = 4986459015
//        0 : 12500
//        50000 : 62500
//        87500 : 100000
//        37500 : 50000
//        62500 : 75000
//        25000 : 37500
//        75000 : 87500
//        12500 : 25000
//        RecursiveTask sum is: 4986459015
//        java.util.concurrent.ForkJoinPool
