package com.example.e_commerce_app;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    private Product product;

    @Before
    public void setUp() {
        product = new Product();
    }

    @Test
    public void testId() {
        String id = "123";
        product.setId(id);
        assertEquals("Product ID should be set correctly", id, product.getId());
    }

    @Test
    public void testName() {
        String name = "Test Product";
        product.setName(name);
        assertEquals("Product name should be set correctly", name, product.getName());
    }

    @Test
    public void testDescription() {
        String description = "This is a test product.";
        product.setDescription(description);
        assertEquals("Product description should be set correctly", description, product.getDescription());
    }

    @Test
    public void testPrice() {
        double price = 99.99;
        product.setPrice(price);
        assertEquals("Product price should be set correctly", price, product.getPrice(), 0.0);
    }

    @Test
    public void testImageUrl() {
        String imageUrl = "http://example.com/image.jpg";
        product.setImageUrl(imageUrl);
        assertEquals("Product image URL should be set correctly", imageUrl, product.getImageUrl());
    }

    @Test
    public void testSellerId() {
        String sellerId = "seller123";
        product.setSellerId(sellerId);
        assertEquals("Product seller ID should be set correctly", sellerId, product.getSellerId());
    }

    @Test
    public void testCategory() {
        String category = "Electronics";
        product.setCategory(category);
        assertEquals("Product category should be set correctly", category, product.getCategory());
    }

    @Test
    public void testParameterizedConstructor() {
        String id = "123";
        String name = "Test Product";
        String description = "This is a test product.";
        double price = 99.99;
        String imageUrl = "http://example.com/image.jpg";
        String sellerId = "seller123";
        String category = "Electronics";

        Product product = new Product(id, name, description, price, imageUrl, sellerId, category);

        assertEquals("Product ID should be set correctly", id, product.getId());
        assertEquals("Product name should be set correctly", name, product.getName());
        assertEquals("Product description should be set correctly", description, product.getDescription());
        assertEquals("Product price should be set correctly", price, product.getPrice(), 0.0);
        assertEquals("Product image URL should be set correctly", imageUrl, product.getImageUrl());
        assertEquals("Product seller ID should be set correctly", sellerId, product.getSellerId());
        assertEquals("Product category should be set correctly", category, product.getCategory());
    }
}
