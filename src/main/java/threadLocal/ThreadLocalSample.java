package threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class ThreadSafeFormatter {
    public static final ThreadLocal<SimpleDateFormat> SDF = ThreadLocal.withInitial(() -> {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Inside init for thread in ThreadLocal");
        return simpleDateFormat;
    });
}

class UserService {
    public String birthDate() {
        return ThreadSafeFormatter.SDF.get().format(new Date());
    }
}

public class ThreadLocalSample {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        UserService us = new UserService();
        for (int i = 0; i < 1000; i++) {
            final int a = i;
            threadPool.submit(() -> {
                System.out.println(a + " " + us.birthDate());
            });
        }
        Thread.sleep(1000);
        threadPool.shutdown();
    }
}
