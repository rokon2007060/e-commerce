package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private Button loginButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                loginUserAccount(email, password);
            }
        });
    }

    private void loginUserAccount(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                                    checkAccessLevel(user.getUid());
                                } else {
                                    Toast.makeText(getApplicationContext(), "User object is null", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Log.e("LoginActivity", "Error in login", e);
                            Toast.makeText(getApplicationContext(), "An error occurred, please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void reg(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    public void fpsw(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            checkAccessLevel(currentUser.getUid());
        }
    }

    private void checkAccessLevel(String uid) {
        DocumentReference df = fstore.collection("users").document(uid);
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d("LoginActivity", "DocumentSnapshot data: " + document.getData());

                        // Check if isAdmin is Boolean
                        Boolean isAdmin = null;
                        Object isAdminField = document.get("isAdmin");
                        if (isAdminField instanceof Boolean) {
                            isAdmin = (Boolean) isAdminField;
                        }

                        // Check if isUser is Boolean
                        Boolean isUser = null;
                        Object isUserField = document.get("isUser");
                        if (isUserField instanceof Boolean) {
                            isUser = (Boolean) isUserField;
                        }

                        // Check if isSeller is Boolean or String
                        Boolean isSeller = null;
                        Object isSellerField = document.get("isSeller");
                        if (isSellerField instanceof Boolean) {
                            isSeller = (Boolean) isSellerField;
                        } else if (isSellerField instanceof String) {
                            isSeller = "1".equals(isSellerField);
                        }

                        if (Boolean.TRUE.equals(isAdmin)) {
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            finish();
                        } else if (Boolean.TRUE.equals(isUser)) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else if (Boolean.TRUE.equals(isSeller)) {
                            startActivity(new Intent(LoginActivity.this, SellerActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("LoginActivity", "No such document");
                        Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("LoginActivity", "get failed with ", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
