package com.example.e_commerce_app;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        // Redirect user to the login screen after logout
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish(); // Close the current activity so that user can't go back with back button
    }
}