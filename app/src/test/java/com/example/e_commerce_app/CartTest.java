package com.example.e_commerce_app;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CartTest {

    private Cart cart;
    private Product product1;
    private Product product2;

    @Before
    public void setUp() {
        cart = Cart.getInstance();
        cart.clearCart();  // Ensure cart is cleared before each test
        product1 = new Product("1", "Product 1", "Description 1", 10.0, "", "Category 1", 4.5f);
        product2 = new Product("2", "Product 2", "Description 2", 20.0, "", "Category 2", 3.5f);
    }

    @Test
    public void testAddItem() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 1);

        List<CartItem> items = cart.getCartItems();
        assertEquals(2, items.size());
        assertEquals(product1.getId(), items.get(0).getProduct().getId());
        assertEquals(2, items.get(0).getQuantity());
        assertEquals(product2.getId(), items.get(1).getProduct().getId());
        assertEquals(1, items.get(1).getQuantity());
    }

    @Test
    public void testAddItemExistingProduct() {
        cart.addItem(product1, 2);
        cart.addItem(product1, 3);  // Adding more quantity to the same product

        List<CartItem> items = cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals(product1.getId(), items.get(0).getProduct().getId());
        assertEquals(5, items.get(0).getQuantity());  // Total quantity should be 5
    }

    @Test
    public void testRemoveItem() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 1);

        cart.removeItem(product1);

        List<CartItem> items = cart.getCartItems();
        assertEquals(1, items.size());
        assertEquals(product2.getId(), items.get(0).getProduct().getId());
    }

    @Test
    public void testClearCart() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 1);

        cart.clearCart();

        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void testGetTotalPrice() {
        cart.addItem(product1, 2);  // 2 * 10.0 = 20.0
        cart.addItem(product2, 1);  // 1 * 20.0 = 20.0

        double totalPrice = cart.getTotalPrice();
        assertEquals(40.0, totalPrice, 0.01);
    }
}
