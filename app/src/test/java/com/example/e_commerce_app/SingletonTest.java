package com.example.e_commerce_app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SingletonTest {

    @Mock
    FirebaseAuth mockAuth;

    @Mock
    FirebaseFirestore mockFirestore;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockStatic(FirebaseAuth.class);
        mockStatic(FirebaseFirestore.class);

        when(FirebaseAuth.getInstance()).thenReturn(mockAuth);
        when(FirebaseFirestore.getInstance()).thenReturn(mockFirestore);
    }

    @Test
    public void testSingletonInstance() {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2); // Both instances should be the same
    }

    @Test
    public void testFirebaseAuthInstance() {
        Singleton instance = Singleton.getInstance();
        FirebaseAuth auth = instance.getAuth();

        assertNotNull(auth);
        assertSame(mockAuth, auth); // The auth instance should be the mocked instance
    }

    @Test
    public void testFirebaseFirestoreInstance() {
        Singleton instance = Singleton.getInstance();
        FirebaseFirestore firestore = instance.getFirestore();

        assertNotNull(firestore);
        assertSame(mockFirestore, firestore); // The firestore instance should be the mocked instance
    }
}
