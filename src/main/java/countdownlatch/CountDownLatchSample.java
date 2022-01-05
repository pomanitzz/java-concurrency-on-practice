package countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchSample {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(new Task(countDownLatch)).start();
        new Thread(new Task(countDownLatch)).start();
        new Thread(new Task(countDownLatch)).start();

        countDownLatch.await();
        System.out.println("The end");
    }

}


class Task implements Runnable {
    CountDownLatch countDownLatch;

    public Task(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Hello world");
        countDownLatch.countDown();
    }
}