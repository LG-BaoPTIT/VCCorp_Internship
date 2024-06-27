package org.example;

public class SimpleDisplayStrategy implements DisplayStrategy {
    @Override
    public void displayCustomerInfo(Customer customer) {
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Accounts:");
        for (Account account : customer.getAccounts()) {
            if (account instanceof SavingsAccount) {
                displaySavingsAccountInfo((SavingsAccount) account);
            } else if (account instanceof CheckingAccount) {
                displayCheckingAccountInfo((CheckingAccount) account);
            }
            System.out.println("-----------------------------");
        }
    }
    @Override
    public void displaySavingsAccountInfo(SavingsAccount savingsAccount) {
        System.out.println("Savings Account: " + savingsAccount.getAccountNumber());
        System.out.println("Balance: $" + savingsAccount.getBalance());
        System.out.println("Interest Rate: " + savingsAccount.getInterestRate());
    }
    @Override
    public void displayCheckingAccountInfo(CheckingAccount checkingAccount) {
        System.out.println("Checking Account: " + checkingAccount.getAccountNumber());
        System.out.println("Balance: $" + checkingAccount.getBalance());
        System.out.println("Overdraft Limit: $" + checkingAccount.getOverdraftLimit());
    }
}
