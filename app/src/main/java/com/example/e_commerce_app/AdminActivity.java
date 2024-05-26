package com.example.e_commerce_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminProductAdapter productAdapter;
    private UserAdapter userAdapter;
    private List<Product> productList;
    private List<User> userList;
    private FirebaseFirestore db;

    private Button btnProducts, btnSellers, btnCustomers;

    private ListenerRegistration userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        userList = new ArrayList<>();
        productAdapter = new AdminProductAdapter(this, productList);
        userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(productAdapter);

        db = FirebaseFirestore.getInstance();

        btnProducts = findViewById(R.id.products);
        btnSellers = findViewById(R.id.sellers);
        btnCustomers = findViewById(R.id.customers);

        btnProducts.setOnClickListener(v -> loadProducts());
        btnSellers.setOnClickListener(v -> loadUsers("isSeller"));
        btnCustomers.setOnClickListener(v -> loadUsers("isUser"));

        loadProducts();
        attachUserListener();
        NotificationHelper.createNotificationChannel(this);
    }

    private void loadProducts() {
        productList.clear();
        db.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot productDoc : task.getResult()) {
                    Product product = productDoc.toObject(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                recyclerView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(AdminActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUsers(String userType) {
        userList.clear();
        db.collection("users").whereEqualTo(userType, "1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot userDoc : task.getResult()) {
                    String fullName = userDoc.getString("full_name");
                    String email = userDoc.getString("email");
                    String phoneNumber = userDoc.getString("phone");
                    String profileImageUrl = userDoc.getString("profileImageUrl");
                    boolean isSeller = "1".equals(userDoc.getString("isSeller"));
                    boolean isUser = "1".equals(userDoc.getString("isUser"));

                    User user = new User(fullName, email, phoneNumber, profileImageUrl, isSeller, isUser);
                    if (user != null) {
                        userList.add(user);
                        Log.d("AdminActivity", "Adding user: " + user.getFullName());
                    }
                }
                recyclerView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            } else {
                Exception e = task.getException();
                Log.d("AdminActivity", "Error loading users: ", e);
                Toast.makeText(AdminActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attachUserListener() {
        userListener = db.collection("users").addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                Log.w("AdminActivity", "Listen failed.", e);
                return;
            }

            if (snapshots != null) {
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            User newUser = dc.getDocument().toObject(User.class);
                            sendNotification(newUser);
                            break;
                        case MODIFIED:
                            // Handle modified document
                            break;
                        case REMOVED:
                            // Handle removed document
                            break;
                    }
                }
            }
        });
    }

    private void sendNotification(User user) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "user_notifications")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New User Added")


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userListener != null) {
            userListener.remove();
        }
    }
}
