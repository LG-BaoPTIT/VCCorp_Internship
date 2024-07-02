package org.example;

import org.example.Singleton_Design_pattern.EagerInitialization;
import org.example.Singleton_Design_pattern.ThreadSafe;

public class Main {
    public static boolean flag = false;
    public static void main(String[] args) {
        EagerInitialization eager1 = EagerInitialization.getInstance();
        eager1.setName("LGB");
        System.out.println(eager1.getName());

        EagerInitialization eager2 = EagerInitialization.getInstance();
        System.out.println(eager2.getName());

        ThreadSafe threadSafe = ThreadSafe.getInstance();

        threadSafe.setName("bao luu");
        System.out.println("thread save"+threadSafe.getName());


        new Thread(() -> {
            while (!flag){
                System.out.println("thread 1"+ threadSafe.getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            while (!flag){
                System.out.println("thread 2"+ threadSafe.getName());
            }
        }).start();


    }
}