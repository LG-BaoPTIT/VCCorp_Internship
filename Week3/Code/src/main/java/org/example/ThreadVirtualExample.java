package org.example;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadVirtualExample {
    private static final int NUM_THREADS = 100000;

    public static void main(String[] args) throws InterruptedException {
        long start;

        start = System.nanoTime();
        createAndStartThreads(false);
        long regularThreadTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        start = System.nanoTime();
        createAndStartThreads(true);
        long virtualThreadTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        System.out.println("Thời gian chạy " + NUM_THREADS + " luồng thông thường: " + regularThreadTime + " ms");
        System.out.println("Thời gian chạy " + NUM_THREADS + " luồng ảo: " + virtualThreadTime + " ms");

        // Sử dụng ExecutorService cho luồng ảo
        start = System.nanoTime();
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.execute(createTask());
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long virtualThreadTime2 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println("Thời gian chạy " + NUM_THREADS + " luồng ảo (ExecutorService): " + virtualThreadTime2 + " ms");
    }

    private static void createAndStartThreads(boolean virtual) {
        for (int i = 0; i < NUM_THREADS; i++) {
            if (virtual) {
                Thread.startVirtualThread(createTask());
            } else {
                new Thread(createTask()).start();
            }
        }
    }

    private static Runnable createTask() {
        return () -> {
            try {
                Thread.sleep(100); // Giả lập một nhiệm vụ chậm
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
