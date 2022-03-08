package adder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Adder is much faster than Atomic.
 */
public class AdderSample {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        Thread.sleep(500);

        CountDownLatch countDownLatch = new CountDownLatch(1000);
        AtomicLong al = new AtomicLong();
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new TaskAtomic(al, countDownLatch));
        }
        countDownLatch.await();
        long atomicTime = System.nanoTime() - startTime;
        System.out.println("Value: " + al.get() + ", Total time ATOMIC: " + atomicTime);

        Thread.sleep(500);

        LongAdder la = new LongAdder();
        countDownLatch = new CountDownLatch(1000);
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new TaskAdder(la, countDownLatch));
        }
        countDownLatch.await();
        long adderTime = System.nanoTime() - startTime;
        System.out.println("Value: " + la.sum() + ", Total time ADDER: " + adderTime);


        Thread.sleep(500);

        System.out.println("Atomic/Adder: " + ((double) atomicTime / (double) adderTime));

        executorService.shutdown();
    }

}


class TaskAtomic implements Runnable {
    private final AtomicLong al;
    private final CountDownLatch countDownLatch;

    public TaskAtomic(AtomicLong al, CountDownLatch countDownLatch) {
        this.al = al;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        al.incrementAndGet();
        countDownLatch.countDown();
    }
}


class TaskAdder implements Runnable {
    private final LongAdder ad;
    private final CountDownLatch countDownLatch;

    public TaskAdder(LongAdder ad, CountDownLatch countDownLatch) {
        this.ad = ad;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        ad.increment();
        countDownLatch.countDown();
    }
}