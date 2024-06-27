package org.example;

public interface DisplayStrategy {
    void displayCustomerInfo(Customer customer);

    void displaySavingsAccountInfo(SavingsAccount savingsAccount);

    void displayCheckingAccountInfo(CheckingAccount checkingAccount);
}
