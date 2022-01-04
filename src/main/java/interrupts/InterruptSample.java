package interrupts;

public class InterruptSample {

    public static void main(String[] args) throws Exception {
        Thread taskInterrupted = new Thread(new TaskInterrupted());
        taskInterrupted.start();
        taskInterrupted.interrupt();

        TaskInterrupted2 param = new TaskInterrupted2();
        Thread taskInterrupted2 = new Thread(param);
        taskInterrupted2.start();
        Thread.sleep(1_000);
        taskInterrupted2.interrupt();
//        synchronized (param.obj) {
//            param.obj.notify();
//        }
    }
}


class TaskInterrupted implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Start TaskInterrupted");
            Thread.sleep(1_000);
            System.out.println("End TaskInterrupted");
        } catch (InterruptedException e) {
            System.err.println("TaskInterrupted");
        }
    }
}


class TaskInterrupted2 implements Runnable {

    public final Object obj = new Object();

    @Override
    public void run() {
        try {
            synchronized (obj) {
                System.out.println("Start TaskInterrupted2");
                obj.wait();
                System.out.println("End TaskInterrupted2");
            }
        } catch (InterruptedException e) {
            System.err.println("TaskInterrupted2");
        }
    }
}


