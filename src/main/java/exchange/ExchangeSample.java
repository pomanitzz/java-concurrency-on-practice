package exchange;

import java.util.concurrent.Exchanger;

public class ExchangeSample {
    public static void main(String[] args) throws Exception {
        Exchanger<String> exchanger = new Exchanger<>();
        Thread firstThread = new Thread(new Task("FirstTask", exchanger));
        Thread secondThread = new Thread(new Task("SecondTask", exchanger));

        firstThread.start();
        Thread.sleep(2_000);
        secondThread.start();
    }
}

class Task implements Runnable {
    private String resource;
    private final Exchanger<String> exchanger;

    public Task(String resource, Exchanger<String> exchanger) {
        this.resource = resource;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            System.out.println("Resource: " + resource + ", Thread: " + Thread.currentThread().getName());
            this.resource = exchanger.exchange(resource);
            System.out.println("Resource: " + resource + ", Thread: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

