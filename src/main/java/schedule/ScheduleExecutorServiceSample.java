package schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleExecutorServiceSample {
    public static void main(String[] args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        scheduledExecutorService.scheduleAtFixedRate(new Task(), 1, 1, TimeUnit.SECONDS);

        Thread.sleep(5_000);
        scheduledExecutorService.shutdown();
    }
}


class Task implements Runnable {

    @Override
    public void run() {
        System.out.println("SOUT from Task, thread: " + Thread.currentThread().getName());
    }
}

