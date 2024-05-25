package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private CustomerProductAdapter productAdapter;
    private List<Product> productList;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private EditText searchBar;
    private ImageView searchButton;
    private Fragment productDetailsFragment;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String currentCategory = "All"; // Default category is "All"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productList = new ArrayList<>();
        productAdapter = new CustomerProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        // Initialize SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProducts1(currentCategory); // Use the current category for refreshing
            }
        });

        loadProducts(currentCategory); // Load the default category on startup

        Button btnAll = findViewById(R.id.btn_all);
        Button btnMedicine = findViewById(R.id.btn_medicine);
        Button btnClothing = findViewById(R.id.btn_clothing);
        Button btnElectronics = findViewById(R.id.btn_electronics);
        Button btnBooks = findViewById(R.id.btn_books);

        ImageView products_cart = findViewById(R.id.products_cart);



        products_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });



        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCategory = "All";
                loadProducts(currentCategory);
            }
        });

        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCategory = "Medicine";
                loadProducts(currentCategory);
            }
        });

        btnClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCategory = "Clothing";
                loadProducts(currentCategory);
            }
        });

        btnElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCategory = "Electronics";
                loadProducts(currentCategory);
            }
        });

        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCategory = "Books";

                loadProducts(currentCategory);
            }
        });

        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProducts(searchBar.getText().toString());
            }
        });

        productDetailsFragment = new ProductDetailsFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_account) {
            // Handle account actions
        } else if (id == R.id.nav_orders) {
            // Handle orders actions
        } else if (id == R.id.logoutButton) {
            Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadProducts(String category) {
        firestore.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> products = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        products.add(product);
                    }
                    ProductCache.loadCache(products);
                    displayProducts(category);
                } else {
                    Toast.makeText(CustomerActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false); // Stop the refresh animation
            }
        });
    }


    private void loadProducts1(String category) {
        firestore.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Product> products = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        products.add(product);
                    }
                    ProductCache.loadCache(products); // Update cache with all products
                    displayProducts(category); // Display products for the specified category
                } else {
                    Toast.makeText(CustomerActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false); // Stop the refresh animation
            }
        });
    }


    private void displayProducts(String category) {
        productList.clear();
        if (category.equals("All")) {
            productList.addAll(ProductCache.getAllProducts());
        } else {
            productList.addAll(ProductCache.getProductsByCategory(category));
        }
        productAdapter.notifyDataSetChanged();
    }

    private void searchProducts(String query) {
        productList.clear();
        productList.addAll(ProductCache.searchProducts(query));
        productAdapter.notifyDataSetChanged();
    }

    // Method to show product details fragment
    private void showProductDetails(Product product) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("productId", product.getId());
        bundle.putString("productName", product.getName());
        bundle.putString("productDescription", product.getDescription());
        bundle.putDouble("productPrice", product.getPrice());
        bundle.putString("productImageUrl", product.getImageUrl());
        bundle.putString("productCategory", product.getCategory());
        bundle.putFloat("productRating", product.getRating());

        productDetailsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, productDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
