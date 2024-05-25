package com.example.e_commerce_app;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCache {

    private static final String TAG = "ProductCache";
    private static Map<String, Product> productMap = new HashMap<>();

    public static void loadCache(List<Product> products) {
        for (Product product : products) {
            productMap.put(product.getId(), product);
            Log.d(TAG, "Product added to cache: " + product.getName() + " (Category: " + product.getCategory() + ")");
        }
    }

    public static Product getProduct(String productId) {
        Product cachedProduct = productMap.get(productId);
        return (cachedProduct != null) ? cachedProduct.clone() : null;
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (Product product : productMap.values()) {
            products.add(product.clone());
        }
        return products;
    }

    public static List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        for (Product product : productMap.values()) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                products.add(product.clone());
            }
        }
        Log.d(TAG, "Products in category '" + category + "': " + products.size());
        return products;
    }

    public static List<Product> searchProducts(String query) {
        List<Product> exactMatches = new ArrayList<>();
        List<Product> partialMatches = new ArrayList<>();

        for (Product product : productMap.values()) {
            if (product.getName().equalsIgnoreCase(query)) {
                exactMatches.add(product.clone());
            } else if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                partialMatches.add(product.clone());
            }
        }

        // Combine the two lists: first exact matches, then partial matches
        List<Product> products = new ArrayList<>();
        products.addAll(exactMatches);
        products.addAll(partialMatches);

        Log.d(TAG, "Products matching query '" + query + "': " + products.size());
        return products;
    }

}
