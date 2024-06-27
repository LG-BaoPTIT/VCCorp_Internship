package org.example;

public class DepositTransaction implements Transaction {
    @Override
    public void execute(Account account, double amount) {
        account.deposit(amount);
    }
}
