package phaser;

import java.util.concurrent.Phaser;

public class PhaserSample {

    public static void main(String[] args) {
        Phaser ph = new Phaser(1);
        int curPhase;
        curPhase = ph.getPhase();
        System.out.println("Phase in atomicref.Main " + curPhase + " started");
        // Threads for first phase
        new FileReaderThread("thread-1", "file-1", ph);
        new FileReaderThread("thread-2", "file-2", ph);
        new FileReaderThread("thread-3", "file-3", ph);
        //For main thread
        ph.arriveAndAwaitAdvance();
        System.out.println("New phase " + ph.getPhase() + " started");
        // Threads for second phase
        new QueryThread("thread-1", 40, ph);
        new QueryThread("thread-2", 40, ph);
        curPhase = ph.getPhase();
        ph.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " completed");
        // deregistering the main thread
        ph.arriveAndDeregister();
    }
}

class FileReaderThread implements Runnable {
    private final String threadName;
    private final String fileName;
    private final Phaser ph;

    FileReaderThread(String threadName, String fileName, Phaser ph) {
        this.threadName = threadName;
        this.fileName = fileName;
        this.ph = ph;
        ph.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("This is phase " + ph.getPhase());

        try {
            Thread.sleep(20);
            System.out.println("Reading file " + fileName + " thread "
                    + threadName + " parsing and storing to DB ");
            // Using await and advance so that all thread wait here
            ph.arriveAndAwaitAdvance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ph.arriveAndDeregister();
    }
}

class QueryThread implements Runnable {
    private final String threadName;
    private final int param;
    private final Phaser ph;

    QueryThread(String threadName, int param, Phaser ph) {
        this.threadName = threadName;
        this.param = param;
        this.ph = ph;
        ph.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("This is phase " + ph.getPhase());
        System.out.println("Querying DB using param " + param
                + " Thread " + threadName);
        ph.arriveAndAwaitAdvance();
        System.out.println("Threads finished");
        ph.arriveAndDeregister();
    }
}