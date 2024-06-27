package org.example;

public class WithdrawTransaction implements Transaction {
    @Override
    public void execute(Account account, double amount) {
        account.withdraw(amount);
    }
}
