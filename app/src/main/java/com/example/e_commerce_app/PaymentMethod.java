package com.example.e_commerce_app;

public interface PaymentMethod {
    void pay(double amount, String merchantNumber, PaymentCallback callback);
}
