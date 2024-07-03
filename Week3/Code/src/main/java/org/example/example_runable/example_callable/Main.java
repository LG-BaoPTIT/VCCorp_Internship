package org.example.example_runable.example_callable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args){
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
        List<SumTask> sumTasks = Arrays.asList(new SumTask(10),new SumTask(20),new SumTask(30));


        try {
            List<Future<Long>> future  = threadPoolExecutor.invokeAll(sumTasks);
            Future<Long> longFuture = threadPoolExecutor.submit(new SumTask(1000));
            System.out.println(longFuture.get());

            for (Future<Long> f : future){
                System.out.println(f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        finally {
            threadPoolExecutor.shutdown();
            try {
                if (!threadPoolExecutor.awaitTermination(20,TimeUnit.SECONDS)){
                    threadPoolExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
