package org.example;

import org.example.example_runable.Account;
import org.example.example_runable.WithdrawTask;
import org.example.example_runable.example_callable.SumTask;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class example_thread_pool {
    public static void main(String[] args) {
        // Cấu hình thread pool
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 1;
        TimeUnit unit = TimeUnit.MINUTES;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                handler
        );

        // Gửi tác vụ tới thread pool
        for (int i = 0; i < 15; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is executing a task.");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }


        List<Callable<String>> tasks = Arrays.asList(
                () -> {
                    TimeUnit.SECONDS.sleep(2);
                    return "Result from Task 1";
                },
                () -> {
                    TimeUnit.SECONDS.sleep(1);
                    return "Result from Task 2";
                },
                () -> "Result from Task 3"
        );


        try {
            List<Future<String>> futures = threadPoolExecutor.invokeAll(tasks);
            for (Future<String> future : futures) {
                try {
                    String result = future.get();
                    System.out.println("Task result: " + result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }

        // Đóng thread pool
        threadPoolExecutor.shutdown();
        try {
            if (!threadPoolExecutor.awaitTermination(60,TimeUnit.SECONDS)) {
                System.out.println("shutdown now!");
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }
    }



}
