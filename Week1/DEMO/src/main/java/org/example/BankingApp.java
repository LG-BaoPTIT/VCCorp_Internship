package org.example;

public class BankingApp {
    public static void main(String[] args) {
        // Khởi tạo khách hàng và các tài khoản
        Customer customer = new Customer("Luu Gia Bao", "12345");
        Account savingsAccount = new SavingsAccount("SA001", 5000.0, 0.02);
        Account checkingAccount = new CheckingAccount("CA001", 2000.0, 1000.0);

        // Thêm tài khoản vào khách hàng
        customer.addAccount(savingsAccount);
        customer.addAccount(checkingAccount);

        // Hiển thị thông tin khách hàng trước khi thực hiện giao dịch
        DisplayStrategy simpleDisplayStrategy = new SimpleDisplayStrategy();
        simpleDisplayStrategy.displayCustomerInfo(customer);
        System.out.println("-----------------------------");

        // Thực hiện các giao dịch
        Transaction depositTransaction = new DepositTransaction();
        Transaction withdrawTransaction = new WithdrawTransaction();

        depositTransaction.execute(savingsAccount, 1000);
        withdrawTransaction.execute(checkingAccount, 3000);

        System.out.println("-----------------------------");
        // Hiển thị thông tin khách hàng sau khi thực hiện giao dịch
        simpleDisplayStrategy.displayCustomerInfo(customer);

    }

}