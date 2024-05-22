package com.example.e_commerce_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.e_commerce_app.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Retrieve user data from Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("full_name");
        String email = intent.getStringExtra("email1");
        String photoUrl = intent.getStringExtra("photoUrl");

        // Update the navigation drawer header with user information
        updateNavHeader(name, email, photoUrl);

        // Update the menu items based on user login status
        updateMenuItems();

        // Handle navigation item selection
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigationItemSelected(item);
            }
        });

        // Refresh header if logged in
        refreshNavHeader();
    }

    private void updateNavHeader(String name, String email, String photoUrl) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView profileImageView = headerView.findViewById(R.id.profile_image);
        TextView profileNameTextView = headerView.findViewById(R.id.profile_name);
        TextView profileEmailTextView = headerView.findViewById(R.id.profile_email);

//        Toast.makeText(this, "Hello " + name  , Toast.LENGTH_LONG).show();
        profileNameTextView.setText(name);
        profileEmailTextView.setText(email);

        if (photoUrl != null && !photoUrl.isEmpty()) {
            Glide.with(this).load(photoUrl).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.mipmap.ic_launcher_round); // Set default image
        }
    }

    public void refreshNavHeader() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            updateNavHeader(name, email, photoUrl != null ? photoUrl.toString() : null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void updateMenuItems() {
        Menu menu = navigationView.getMenu();
        boolean isLoggedIn = checkLoginStatus(); // Implement this method to check login status
        menu.findItem(R.id.nav_login).setVisible(!isLoggedIn);
        menu.findItem(R.id.nav_register).setVisible(!isLoggedIn);
        menu.findItem(R.id.nav_logout).setVisible(isLoggedIn);
    }

    private boolean checkLoginStatus() {
        // Implement logic to check if the user is logged in
        // Return true if logged in, false otherwise
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle home action
        } else if (id == R.id.nav_gallery) {
            // Handle gallery action
        } else if (id == R.id.nav_slideshow) {
            // Handle slideshow action
        } else if (id == R.id.nav_login) {
            // Handle login action
            // Navigate to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_register) {
            // Handle register action
            // Navigate to RegistrationActivity
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            // Handle logout action
            // Perform logout logic
            logout();
        }

        DrawerLayout drawer = binding.drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        // Perform logout logic, such as clearing user session data
        FirebaseAuth.getInstance().signOut();
        updateMenuItems();
        Snackbar.make(binding.drawerLayout, "Logged out", Snackbar.LENGTH_SHORT).show();
    }
}
