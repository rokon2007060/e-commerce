package com.example.e_commerce_app;

public class NagadPayment implements PaymentMethod {
    @Override
    public void pay(double amount, String merchantNumber, PaymentCallback callback) {
        // Simulate Nagad payment logic
        NagadPaymentRequest request = new NagadPaymentRequest();
        request.setAmount(amount);
        request.setMerchantNumber(merchantNumber);
        // Other necessary details

        // Simulate API call
        boolean paymentSuccess = simulateNagadPayment(request);

        if (paymentSuccess) {
            callback.onSuccess();
        } else {
            callback.onFailure("Nagad payment failed");
        }
    }

    private boolean simulateNagadPayment(NagadPaymentRequest request) {
        // Simulate Nagad payment logic
        return true; // or false based on your logic
    }
}



