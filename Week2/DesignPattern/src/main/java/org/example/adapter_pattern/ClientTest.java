package org.example.adapter_pattern;

public class ClientTest {
    public static void main(String[] args) {
        Customer customer = new BusinessAnalyst(new Developer());
        customer.sendRequest("I want to work from home!");
    }
}
