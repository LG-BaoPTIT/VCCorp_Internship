package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    private AtomicInteger count = new AtomicInteger(0);
    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
    public static int x=0;
    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerExample example = new AtomicIntegerExample();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
                x+=1;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
                x+=1;
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final Count: " + example.getCount()); // Kết quả mong đợi là 2000
        System.out.println("Final Count: " + x); // Kết quả mong đợi là 2000

    }
}

