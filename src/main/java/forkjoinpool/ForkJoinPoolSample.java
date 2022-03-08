package forkjoinpool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolSample {

    public static void main(String[] args) throws Exception {
        int[] array = getInitArray(10_000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        ValueSumCounter counter = new ValueSumCounter(array);
        System.out.println(forkJoinPool.invoke(counter));
        System.out.println("Total time with ForkJoinPool: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        System.out.println(countInOneThread(array));
        System.out.println("Total time in one thread: " + (System.currentTimeMillis() - startTime));
    }

    public static long countInOneThread(int[] array) {
        long sum = 0;
        for (int j : array) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum += j;
        }
        return sum;
    }

    public static int[] getInitArray(int capacity) {
        int[] array = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = 1;
        }
        return array;
    }
}

class ValueSumCounter extends RecursiveTask<Integer> {

    private final int[] array;

    public ValueSumCounter(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 500) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
//            System.out.printf("Task %s execute in thread %s%n", this, Thread.currentThread().getName());
            }
            return (int) ForkJoinPoolSample.countInOneThread(array);
        }
        ValueSumCounter firstHalfArrayValueSumCounter = new ValueSumCounter(Arrays.copyOfRange(array, 0, array.length / 2));
        ValueSumCounter secondHalfArrayValueSumCounter = new ValueSumCounter(Arrays.copyOfRange(array, array.length / 2, array.length));
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();
        return firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join();
    }
}
