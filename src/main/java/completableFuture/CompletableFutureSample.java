package completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureSample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long nanoTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new Random().nextInt();
            })
                    .thenApply(random -> {
                        System.out.println(random);
                        return random;
                    });
        }
        System.out.println("total time: " + (System.nanoTime() - nanoTime));
        Thread.sleep(1000);
        System.out.println("the end");
    }
}
