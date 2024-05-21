package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        txt = findViewById(R.id.prf);

        // Check if user is logged in
        if (mAuth.getCurrentUser() != null) {
            // Get the current user's UID
            String currentUserUid = mAuth.getCurrentUser().getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(currentUserUid) // Query the document with the current user's UID
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Access individual document/data here
                                String fullName = document.getString("full_name");
                                String email = document.getString("email");
                                String phone = document.getString("phone");
                                String address = document.getString("address");

                                // Construct the user data string
                                StringBuilder userData = new StringBuilder();
                                userData.append("Full Name: ").append(fullName).append("\n");
                                userData.append("Email: ").append(email).append("\n");
                                userData.append("Phone: ").append(phone).append("\n");
                                userData.append("Address: ").append(address);

                                // Set the user data to the TextView
                                txt.setText(userData.toString());
                            } else {
                                // Document does not exist
                                txt.setText("User data not found");
                            }
                        } else {
                            // Handle errors
                            txt.setText("Error fetching user data");
                        }
                    });
        } else {
            // User is not logged in, redirect to login screen
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void log(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
