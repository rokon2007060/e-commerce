package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycler_view_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        loadProducts("All");

        Button btnAll = findViewById(R.id.btn_all);
        Button btnMedicine = findViewById(R.id.btn_medicine);
        Button btnClothing = findViewById(R.id.btn_clothing);
        Button btnElectronics = findViewById(R.id.btn_electronics);
        Button btnBooks = findViewById(R.id.btn_books);

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SellerActivity.this, "All products", Toast.LENGTH_SHORT).show();
                loadProducts("All");
            }
        });

        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts("Medicine");
            }
        });

        btnClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts("Clothing");
            }
        });

        btnElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts("Electronics");
            }
        });

        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProducts("Books");
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_add_product) {
            startActivity(new Intent(this, AddActivity.class));
        } else if (id == R.id.logoutButton) {
            Toast.makeText(this, "Logging out as Seller", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadProducts(String category) {
        productList.clear();
        String userId = mAuth.getCurrentUser().getUid();

        if (category.equals("All")) {
            firestore.collection("products")
                    .whereEqualTo("sellerId", userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    productList.add(product);
                                }
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        } else {
            firestore.collection("products")
                    .whereEqualTo("sellerId", userId)
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    productList.add(product);
                                }
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}
