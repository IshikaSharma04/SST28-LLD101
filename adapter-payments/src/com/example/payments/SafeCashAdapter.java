package com.example.payments;

import java.util.Objects;

public class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient client;

    public SafeCashAdapter(SafeCashClient client) {
        this.client = Objects.requireNonNull(client);
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId);
        return client.pay(customerId, amountCents);
    }
}
