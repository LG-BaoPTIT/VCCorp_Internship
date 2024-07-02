package org.example;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }



    @Override
    public void withdraw(double amount) {
        double availableBalance = getBalance() + overdraftLimit;
        if (amount > 0 && amount <= availableBalance) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawal of $" + amount + " successful.");
        } else {
            System.out.println("Withdrawal amount exceeds available balance ($" + availableBalance + ").");
        }
    }

}
