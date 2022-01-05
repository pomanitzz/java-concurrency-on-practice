package accumulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;

public class AccumulatorSample {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Task(longAccumulator));
        }
        Thread.sleep(2_000);
        System.out.println("Result: " + longAccumulator.get());

        executorService.shutdown();
    }
}

class Task implements Runnable {
    private final LongAccumulator longAccumulator;

    public Task(LongAccumulator longAccumulator) {
        this.longAccumulator = longAccumulator;
    }

    @Override
    public void run() {
        longAccumulator.accumulate(2);
    }
}
