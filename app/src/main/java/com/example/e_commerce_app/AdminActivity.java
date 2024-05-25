package com.example.e_commerce_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminProductAdapter adapter;
    private List<Product> productList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new AdminProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadProducts();
    }

    private void loadProducts() {
        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot userDoc : task.getResult()) {
                    if (userDoc.contains("isSeller")) {
                        userDoc.getReference().collection("isSeller").get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                for (DocumentSnapshot productDoc : task1.getResult()) {
                                    Product product = productDoc.toObject(Product.class);
                                    if (product != null) {
                                        productList.add(product);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(AdminActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            } else {
                Toast.makeText(AdminActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
