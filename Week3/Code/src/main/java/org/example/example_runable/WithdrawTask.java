package org.example.example_runable;

public class WithdrawTask implements Runnable{
    private Account account;
    private double amount;

    public WithdrawTask(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}
