package com.example.e_commerce_app;
import com.example.e_commerce_app.Singleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SingletonTest {

    private Singleton singleton;

    @Before
    public void setUp() {
        singleton = Singleton.getInstance();
    }

    @Test
    public void testGetInstance_NotNull() {
        assertNotNull("Singleton instance should not be null", singleton);
    }

    @Test
    public void testGetAuth_NotNull() {
        FirebaseAuth mAuth = singleton.getAuth();
        assertNotNull("FirebaseAuth instance should not be null", mAuth);
    }

    @Test
    public void testGetFirestore_NotNull() {
        FirebaseFirestore fstore = singleton.getFirestore();
        assertNotNull("FirebaseFirestore instance should not be null", fstore);
    }

    @Test
    public void testSingletonInstance_SameInstance() {
        Singleton anotherInstance = Singleton.getInstance();
        assertSame("Both instances should be the same", singleton, anotherInstance);
    }
}
