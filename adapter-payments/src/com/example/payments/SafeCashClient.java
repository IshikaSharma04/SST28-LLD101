package com.example.payments;

public class SafeCashClient {
    public SafeCashPayment createPayment(int amount, String user) {
        return new SafeCashPayment(amount, user);
    }
    public String pay(String user, int amount) {
        return "SC#" + user + ":" + amount;
    }
}
