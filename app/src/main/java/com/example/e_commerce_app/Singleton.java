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

    public void setAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public FirebaseFirestore getFirestore() {
        return fstore;
    }

    public void setFirestore(FirebaseFirestore fstore) {
        this.fstore = fstore;
    }
}
