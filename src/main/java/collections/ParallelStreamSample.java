package collections;

import java.util.ArrayList;
import java.util.List;

public class ParallelStreamSample {
    public static void main(String[] args) {
        System.out.println("Runtime.getRuntime().availableProcessors() : " + Runtime.getRuntime().availableProcessors());
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        list.forEach(el -> {
            System.out.print(" Element: " + el + ", Thread: " + Thread.currentThread().getName());
        });

        System.out.println();
        list.parallelStream().forEach(el -> {
            System.out.print(" Element: " + el + ", Thread: " + Thread.currentThread().getName());
        });
    }
}
