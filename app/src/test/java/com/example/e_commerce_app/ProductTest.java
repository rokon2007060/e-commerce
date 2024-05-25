package com.example.e_commerce_app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    private Product product;

    @Before
    public void setUp() {
        product = new Product("1", "Test Product", "This is a test product", 99.99, "http://example.com/image.jpg", "Electronics", 4.5f);
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("1", product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("This is a test product", product.getDescription());
        assertEquals(99.99, product.getPrice(), 0);
        assertEquals("http://example.com/image.jpg", product.getImageUrl());
        assertEquals("Electronics", product.getCategory());
        assertEquals(4.5f, product.getRating(), 0);

        product.setId("2");
        product.setName("New Product");
        product.setDescription("New description");
        product.setPrice(199.99);
        product.setImageUrl("http://example.com/newimage.jpg");
        product.setCategory("Books");
        product.setRating(5.0f);

        assertEquals("2", product.getId());
        assertEquals("New Product", product.getName());
        assertEquals("New description", product.getDescription());
        assertEquals(199.99, product.getPrice(), 0);
        assertEquals("http://example.com/newimage.jpg", product.getImageUrl());
        assertEquals("Books", product.getCategory());
        assertEquals(5.0f, product.getRating(), 0);
    }

    @Test
    public void testDefaultConstructor() {
        Product defaultProduct = new Product();
        assertNotNull(defaultProduct);
        assertEquals(0, defaultProduct.getRating(), 0);
    }

    @Test
    public void testClone() {
        Product clonedProduct = product.clone();
        assertNotNull(clonedProduct);
        assertEquals(product.getId(), clonedProduct.getId());
        assertEquals(product.getName(), clonedProduct.getName());
        assertEquals(product.getDescription(), clonedProduct.getDescription());
        assertEquals(product.getPrice(), clonedProduct.getPrice(), 0);
        assertEquals(product.getImageUrl(), clonedProduct.getImageUrl());
        assertEquals(product.getCategory(), clonedProduct.getCategory());
        assertEquals(product.getRating(), clonedProduct.getRating(), 0);

        // Check that they are not the same object
        assertNotSame(product, clonedProduct);
    }
}
