package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    private static ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () ->{
            for (int i=0 ; i<1000 ;i++){
                concurrentHashMap.compute("key", (k, v) -> (v == null) ? 1 : v + 1);
            }
        };

        Thread thread = new Thread(task);
        Thread thread1 = new Thread(task);

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();


        System.out.println(concurrentHashMap.get("key"));
    }

}
