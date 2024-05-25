package com.example.e_commerce_app;

import java.util.List;

public class ShopFacade {
    private static ShopFacade instance;

    private ShopFacade() {}

    public static synchronized ShopFacade getInstance() {
        if (instance == null) {
            instance = new ShopFacade();
        }
        return instance;
    }

    public void addToCart(String productId, int quantity) {
        Product product = ProductCache.getProduct(productId);
        if (product != null) {
            Cart.getInstance().addItem(product, quantity);
        }
    }

    public void removeFromCart(String productId) {
        Product product = ProductCache.getProduct(productId);
        if (product != null) {
            Cart.getInstance().removeItem(product);
        }
    }

    public List<CartItem> getCartItems() {
        return Cart.getInstance().getCartItems();
    }

    public double getTotalPrice() {
        return Cart.getInstance().getTotalPrice();
    }

    public void clearCart() {
        Cart.getInstance().clearCart();
    }

    public List<Product> getAllProducts() {
        return ProductCache.getAllProducts();
    }

    public List<Product> getProductsByCategory(String category) {
        return ProductCache.getProductsByCategory(category);
    }

    public List<Product> searchProducts(String query) {
        return ProductCache.searchProducts(query);
    }
}
