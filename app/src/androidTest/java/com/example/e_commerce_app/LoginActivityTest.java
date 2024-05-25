package com.example.e_commerce_app;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Before
    public void setUp() {
        FirebaseAuth mockAuth = MockFirebaseAuth.getMockFirebaseAuth();
        Singleton.getInstance().setAuth(mockAuth);  // Ensure Singleton uses mock auth
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.e_commerce_app", appContext.getPackageName());
    }

    @Test
    public void testActivityLaunch() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginWithEmptyEmail() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Click the login button without entering email or password
        onView(withId(R.id.login)).perform(click());

        // Check if the Toast message is displayed
        onView(withText("Please enter email!!"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
