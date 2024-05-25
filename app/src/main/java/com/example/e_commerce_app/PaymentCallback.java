package com.example.e_commerce_app;

public interface PaymentCallback {
    void onSuccess();
    void onFailure(String error);
}
