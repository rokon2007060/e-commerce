package com.example.e_commerce_app;

public class PaymentFactory {
    public static PaymentMethod getPaymentMethod(String method) {
        switch (method) {
            case "Bkash":
                return new BkashPayment();
            case "Nagad":
                return new NagadPayment();
            case "Rocket":
                return new RocketPayment();
            default:
                throw new IllegalArgumentException("Invalid payment method: " + method);
        }
    }
}

