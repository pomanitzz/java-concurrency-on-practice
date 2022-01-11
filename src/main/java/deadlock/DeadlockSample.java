package deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. jps           - will show all java PIDs
 * 2. jstack {PID}  - will show stacktrace
 *
 * ps aux - in terminal, to find PID of java process ids
 * jstack 39180 > ./out.txt - to print stack trace into ./out.txt file
 *
 * Will see int out.txt logs about deadlock. Looks like this:
 *
 * JNI global refs: 15, weak refs: 0
 *
 *
 * Found one Java-level deadlock:
 * =============================
 * "Thread-0":
 *   waiting for ownable synchronizer 0x000000070ffcb638, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
 *   which is held by "Thread-1"
 *
 * "Thread-1":
 *   waiting for ownable synchronizer 0x000000070ffcb608, (a java.util.concurrent.locks.ReentrantLock$NonfairSync),
 *   which is held by "Thread-0"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "Thread-0":
 * 	at jdk.internal.misc.Unsafe.park(java.base@17.0.1/Native Method)
 * 	- parking to wait for  <0x000000070ffcb638> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
 * 	at java.util.concurrent.locks.LockSupport.park(java.base@17.0.1/LockSupport.java:211)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@17.0.1/AbstractQueuedSynchronizer.java:715)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@17.0.1/AbstractQueuedSynchronizer.java:938)
 * 	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@17.0.1/ReentrantLock.java:153)
 * 	at java.util.concurrent.locks.ReentrantLock.lock(java.base@17.0.1/ReentrantLock.java:322)
 * 	at deadlock.DeadlockSample.processThis(DeadlockSample.java:23)
 * 	at deadlock.DeadlockSample$$Lambda$14/0x0000000800c01228.run(Unknown Source)
 * 	at java.lang.Thread.run(java.base@17.0.1/Thread.java:833)
 * "Thread-1":
 * 	at jdk.internal.misc.Unsafe.park(java.base@17.0.1/Native Method)
 * 	- parking to wait for  <0x000000070ffcb608> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
 * 	at java.util.concurrent.locks.LockSupport.park(java.base@17.0.1/LockSupport.java:211)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@17.0.1/AbstractQueuedSynchronizer.java:715)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@17.0.1/AbstractQueuedSynchronizer.java:938)
 * 	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@17.0.1/ReentrantLock.java:153)
 * 	at java.util.concurrent.locks.ReentrantLock.lock(java.base@17.0.1/ReentrantLock.java:322)
 * 	at deadlock.DeadlockSample.processThat(DeadlockSample.java:36)
 * 	at deadlock.DeadlockSample$$Lambda$15/0x0000000800c01450.run(Unknown Source)
 * 	at java.lang.Thread.run(java.base@17.0.1/Thread.java:833)
 *
 * Found 1 deadlock.
 */
public class DeadlockSample {
    private final Lock LOCK_A = new ReentrantLock();
    private final Lock LOCK_B = new ReentrantLock();

    public static void main(String[] args) {
        new DeadlockSample().runDeadLockScenario();
    }

    public void runDeadLockScenario() {
        new Thread(this::processThis).start();
        new Thread(this::processThat).start();
    }

    public void processThis() {
        try {
            LOCK_A.lock();
            Thread.sleep(1);
            LOCK_B.lock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOCK_A.unlock();
            LOCK_B.unlock();
        }
    }

    public void processThat() {
        try {
            LOCK_B.lock();
            Thread.sleep(1);
            LOCK_A.lock();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOCK_B.unlock();
            LOCK_A.unlock();
        }
    }
}
