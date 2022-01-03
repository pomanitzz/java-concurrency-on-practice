package locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Can get the same result if use synchronized for method or inside method.

WITHOUT LOCKS:

Threads access to resource:Thread-0Thread-1Thread-4
Threads access to resource:Thread-0Thread-1Thread-4Thread-3Thread-2
Threads access to resource:Thread-0Thread-1Thread-4Thread-3
Threads access to resource:Thread-0Thread-1Thread-4Thread-3
Threads access to resource:Thread-0Thread-1Thread-4Thread-3


WITH LOCKS:

Threads access to resource:Thread-1
Threads access to resource:Thread-1Thread-0
Threads access to resource:Thread-1Thread-0Thread-2
Threads access to resource:Thread-1Thread-0Thread-2Thread-3
Threads access to resource:Thread-1Thread-0Thread-2Thread-3Thread-4
 */
public class ReentrantLockSample {

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(ReentrantLockSample::accessResource);
        Thread t2 = new Thread(ReentrantLockSample::accessResource);
        Thread t3 = new Thread(ReentrantLockSample::accessResource);
        Thread t4 = new Thread(ReentrantLockSample::accessResource);
        Thread t5 = new Thread(ReentrantLockSample::accessResource);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    private static void accessResource() {
        lock.lock();
        SomeResource.STRING_BUILDER.append(Thread.currentThread().getName());
        System.out.println(SomeResource.STRING_BUILDER.toString());
        lock.unlock();
    }
}

class SomeResource {
    static StringBuffer STRING_BUILDER = new StringBuffer("Threads access to resource:");
}

