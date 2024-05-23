package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, phoneEditText, addressEditText;
    private Button registerButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private boolean valid = true;
    private CheckBox isAdminBox, isUserBox, isSellerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        fullNameEditText = findViewById(R.id.full_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.passwd);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        registerButton = findViewById(R.id.btnregister);
        progressBar = findViewById(R.id.progressbar);
        isAdminBox = findViewById(R.id.isAdmin);
        isUserBox = findViewById(R.id.isUser);
        isSellerBox = findViewById(R.id.isSeller);

        isAdminBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isUserBox.setChecked(false);
                    isSellerBox.setChecked(false);
                }
            }
        });

        isUserBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isAdminBox.setChecked(false);
                    isSellerBox.setChecked(false);
                }
            }
        });

        isSellerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    isAdminBox.setChecked(false);
                    isUserBox.setChecked(false);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String fullName = fullNameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String phone = phoneEditText.getText().toString().trim();
        final String address = addressEditText.getText().toString().trim();

        // Check if either of the checkboxes is checked
        if (!isUserBox.isChecked() && !isAdminBox.isChecked() && !isSellerBox.isChecked()) {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
            return;
        }

        valid = checkField(fullNameEditText) && checkField(emailEditText) && checkField(passwordEditText) && checkField(phoneEditText) && checkField(addressEditText);

        if (!valid) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("full_name", fullName);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("address", address);
                            if (isAdminBox.isChecked()) {
                                user.put("isAdmin", "1");
                            }
                            if (isUserBox.isChecked()) {
                                user.put("isUser", "1");
                            }
                            if (isSellerBox.isChecked()) {
                                user.put("isSeller", "1");
                            }
                            firestore.collection("users")
                                    .document(mAuth.getCurrentUser().getUid())
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {
                                                if (isAdminBox.isChecked()) {
                                                    startActivity(new Intent(RegistrationActivity.this, AdminActivity.class));
                                                } else if (isUserBox.isChecked()) {
                                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                                } else if (isSellerBox.isChecked()) {
                                                    startActivity(new Intent(RegistrationActivity.this, SellerActivity.class));
                                                }
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Failed to register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void log(View view) {
        startActivity(new Intent (RegistrationActivity.this, LoginActivity.class));
        finish();
    }

    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Please insert information. This field is empty.");
            return false;
        }
        return true;
    }
}

