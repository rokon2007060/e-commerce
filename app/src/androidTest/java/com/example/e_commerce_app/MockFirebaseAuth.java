package com.example.e_commerce_app;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MockFirebaseAuth {
    public static FirebaseAuth getMockFirebaseAuth() {
        FirebaseAuth auth = mock(FirebaseAuth.class);
        FirebaseUser user = mock(FirebaseUser.class);

        // Mock behaviors
        when(user.getUid()).thenReturn("test_uid");
        when(auth.getCurrentUser()).thenReturn(user);

        return auth;
    }
}
