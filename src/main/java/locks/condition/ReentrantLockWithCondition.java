package locks.condition;

import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWithCondition {

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ReentrantLockWithCondition rlwc = new ReentrantLockWithCondition();
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            countDownLatch.await();
            for (int i = 0; i < 10; i++) {
                rlwc.pushToStack(i + "");
            }
            return null;
        });

        executorService.submit(() -> {
            countDownLatch.await();
            for (int i = 0; i < 10; i++) {
                rlwc.popFromStack();
            }
            return null;
        });

        countDownLatch.countDown();

        Thread.sleep(1_000);

        executorService.shutdown();
    }

    Stack<String> stack = new Stack<>();
    int CAPACITY = 5;

    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();

    public void pushToStack(String item) throws Exception {
        try {
            lock.lock();
            while (stack.size() == CAPACITY) {
                System.out.println("push, before await");
                stackFullCondition.await();
                System.out.println("push, after await");
            }
            stack.push(item);
            System.out.println("push, before signalAll");
            stackEmptyCondition.signalAll();
            System.out.println("push, after signalAll");
        } finally {
            lock.unlock();
        }
    }

    public String popFromStack() throws Exception {
        try {
            lock.lock();
            while (stack.size() == 0) {
                System.out.println("pop, before await");
                stackEmptyCondition.await();
                System.out.println("pop, after await");
            }
            return stack.pop();
        } finally {
            System.out.println("pop, before signalAll");
            stackFullCondition.signalAll();
            System.out.println("pop, after signalAll");
            lock.unlock();
        }
    }
}
