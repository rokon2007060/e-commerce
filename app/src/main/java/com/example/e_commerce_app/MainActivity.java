package com.example.e_commerce_app;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        txt=findViewById(R.id.prf);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Access individual documents/data here
                            String fullName = document.getString("full_name");
                            String email = document.getString("email");
                            String phone = document.getString("phone");
                            String address = document.getString("address");
                            StringBuilder userData = new StringBuilder();
                            userData.append("Full Name: ").append(fullName).append("\n");
                            userData.append("Email: ").append(email).append("\n");
                            userData.append("Phone: ").append(phone).append("\n");
                            userData.append("Address: ").append(address);

                            txt.setText(userData.toString());
                            // Use retrieved data as needed
                        }
                    } else {
                        // Handle errors
                    }
                });

    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        // Redirect user to the login screen after logout
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish(); // Close the current activity so that user can't go back with back button
    }
}