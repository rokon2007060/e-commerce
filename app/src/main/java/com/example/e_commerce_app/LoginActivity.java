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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private Button loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
                    public void onComplete(Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    fetchUserData(user.getUid());
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



    private void fetchUserData(String uid) {
        db.collection("users").document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String fullName = document.getString("full_name");
                                String email1 = document.getString("email");
                                Toast.makeText(getApplicationContext(), "Fetching user data found  " + fullName , Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("full_name", fullName);
                                intent.putExtra("email", email1);
                                intent.putExtra("photoUrl", "");
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "No such user data found", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.e("LoginActivity", "Error fetching user data", task.getException());
                            Toast.makeText(getApplicationContext(), "An error occurred while fetching user data", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


//    private void fetchUserData(String uid) {
//        db.collection("users").document(uid).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//
//                    Toast.makeText(this,"Fetching data",Toast.LENGTH_LONG.show());
//                    @Override
//                    public void onComplete(Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                String fullName = document.getString("full_name");
//                                String email = document.getString("email");
//
////                                Toast.makeText(getApplicationContext(), "Welcome " + fullName, Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                intent.putExtra("full_name", fullName);
//                                intent.putExtra("email", email);
//                                intent.putExtra("photoUrl", "");
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(getApplicationContext(), "No such user data found", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Log.e("LoginActivity", "Error fetching user data", task.getException());
//                            Toast.makeText(getApplicationContext(), "An error occurred while fetching user data", Toast.LENGTH_LONG).show();
//                        }
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
//    }

    public void reg(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    public void fpsw(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }
}
