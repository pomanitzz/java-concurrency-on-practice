package cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierSample {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        executorService.submit(new Task(cyclicBarrier));
        executorService.submit(new Task(cyclicBarrier));
        executorService.submit(new Task(cyclicBarrier));

        Thread.sleep(3_000);
        executorService.shutdown();
    }
}


class Task implements Runnable {
    CyclicBarrier cyclicBarrier;

    public Task(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        while (true) {
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Do some operation  cyclicBarrier: " + System.nanoTime());
            break;
        }
    }
}