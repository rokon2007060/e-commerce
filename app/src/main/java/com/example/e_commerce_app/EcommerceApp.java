// MyApplication.java
package com.example.e_commerce_app;

import android.app.Application;
import com.google.firebase.firestore.FirebaseFirestore;

public class EcommerceApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
    }
}
