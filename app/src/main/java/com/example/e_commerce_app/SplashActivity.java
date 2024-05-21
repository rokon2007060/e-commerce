package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 3000; // 3 seconds
    private ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        progressbar = findViewById(R.id.progressBar);
        progressbar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your main activity here
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                progressbar.setVisibility(View.GONE);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}