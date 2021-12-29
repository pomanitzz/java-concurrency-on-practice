package visAndSyncProblems;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Synchronization issue.
 * someVal++ - is not atomic operation.
 * So, for example, if someVal = 5,
 * thread1 read this value(5), thread2 read this value(5), thread1 increment it, thread2 increment it too,
 * thread1 set new value = 6, thread2 set new value - the same value = 6!!!
 *
 * Fixes:
 * 1. Add synchronized to increment section
 * 2. Change someValue type from int to AtomicInteger
 */
public class SyncProblem {

    private int someVal = 0;
// 2.    private final AtomicInteger someVal = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        SyncProblem syncProblem = new SyncProblem();
        new Thread(() -> {
            for (int i = 0; i < 250; i++) {
                syncProblem.increment();
                syncProblem.setInMap(map);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 250; i++) {
                syncProblem.increment();
                syncProblem.setInMap(map);
            }
        }).start();
        Thread.sleep(250);
        System.out.println("Show all issues:");
        map.entrySet().stream().filter(entry -> entry.getValue() > 1)
                .forEach(ent -> System.out.println(ent.getKey() + " " + ent.getValue()));
        System.out.println("The end");
    }


    private void setInMap(ConcurrentHashMap<Integer, Integer> map) {
        if (someVal >= 500) {
            System.out.println("system exit");
            System.exit(0);
        }
        map.merge(someVal, 1, Integer::sum);
    }

// 2.
//    private void setInMap(ConcurrentHashMap<Integer, Integer> map) {
//        if (someVal.get() >= 500) {
//            System.out.println("system exit");
//            System.exit(0);
//        }
//        map.merge(someVal.get(), 1, Integer::sum);
//    }

// 1.    private synchronized void increment() {
    private void increment() {
        someVal++;
    }

// 2.
//    private void increment() {
//        someVal.incrementAndGet();
//    }
}
