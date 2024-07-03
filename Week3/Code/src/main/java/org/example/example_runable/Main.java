package org.example.example_runable;

public class Main {
    public static void main(String[] args) {
        Account account = new Account(1000);
        Thread thread1 = new Thread(new WithdrawTask(account,300),"Thread 1");
        Thread thread2 = new Thread(new WithdrawTask(account,800),"Thread 2");

        thread1.start();
        thread2.start();
    }
}
