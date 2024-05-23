package com.example.e_commerce_app;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Singleton {
    private static Singleton instance;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;

    private Singleton() {
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        Log.d("Singleton", "Singleton instance created");
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseFirestore getFirestore() {
        return fstore;
    }
}
