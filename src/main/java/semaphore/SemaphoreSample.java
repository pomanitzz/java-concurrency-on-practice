package semaphore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreSample {

    public static final Semaphore SEMAPHORE = new Semaphore(2);

    public static void main(String[] args) throws Exception {
        withSemaphoreSample();
        Thread.sleep(1_000);
        withoutSemaphoreSample();
        Thread.sleep(1_000);
    }

    public static void withSemaphoreSample() {
        SemaphoreSample sample = new SemaphoreSample();
        long startTime = System.nanoTime();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            CompletableFuture
                    .supplyAsync(() -> sample.someAccessToSomeAPI(i, true))
                    .thenApply(j -> {
                        if (j == 100) {
                            System.out.println("Total time WITH semaphore: " + (System.nanoTime() - startTime) / 1_000);
                        }
                        return j;
                    });
        });
    }

    public static void withoutSemaphoreSample() {
        SemaphoreSample sample = new SemaphoreSample();
        long startTime = System.nanoTime();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            CompletableFuture
                    .supplyAsync(() -> sample.someAccessToSomeAPI(i, false))
                    .thenApply(j -> {
                        if (j == 100) {
                            System.out.println("Total time WITHOUT semaphore: " + (System.nanoTime() - startTime) / 1_000);
                        }
                        return j;
                    });
        });
    }

    public int someAccessToSomeAPI(int param, boolean semaphoreSample) {
        try {
            if (semaphoreSample) {
                SEMAPHORE.acquire();
            }
            Thread.sleep(10);
            if (semaphoreSample) {
                SEMAPHORE.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }
}
