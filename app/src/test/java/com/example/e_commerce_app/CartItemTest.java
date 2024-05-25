package com.example.e_commerce_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CartItemTest {

    @Test
    public void testCartItemConstructor() {
        // Create a Product object
        Product product = new Product("123", "Test Product", "Description", 19.99, "https://example.com/image.jpg", "Electronics", 4.5f);

        // Create a CartItem object
        CartItem cartItem = new CartItem(product, 2);

        // Check if the CartItem and Product are not null
        assertNotNull(cartItem);
        assertNotNull(cartItem.getProduct());

        // Check if the quantity is set correctly
        assertEquals(2, cartItem.getQuantity());

        // Check if the getProduct method returns the correct Product object
        assertEquals(product, cartItem.getProduct());
    }

    @Test
    public void testCartItemSetQuantity() {
        // Create a Product object
        Product product = new Product("123", "Test Product", "Description", 19.99, "https://example.com/image.jpg", "Electronics", 4.5f);

        // Create a CartItem object
        CartItem cartItem = new CartItem(product, 2);

        // Set a new quantity
        cartItem.setQuantity(5);

        // Check if the quantity is updated correctly
        assertEquals(5, cartItem.getQuantity());
    }
}
