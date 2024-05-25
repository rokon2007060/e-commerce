package com.example.e_commerce_app;

public class RocketPayment implements PaymentMethod {
    @Override
    public void pay(double amount, String merchantNumber, PaymentCallback callback) {
        // Simulate Rocket payment logic
        RocketPaymentRequest request = new RocketPaymentRequest();
        request.setAmount(amount);
        request.setMerchantNumber(merchantNumber);
        // Other necessary details

        // Simulate API call
        boolean paymentSuccess = simulateRocketPayment(request);

        if (paymentSuccess) {
            callback.onSuccess();
        } else {
            callback.onFailure("Rocket payment failed");
        }
    }

    private boolean simulateRocketPayment(RocketPaymentRequest request) {
        // Simulate Rocket payment logic
        return true; // or false based on your logic
    }
}


