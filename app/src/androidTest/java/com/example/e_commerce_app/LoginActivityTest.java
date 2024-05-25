package com.example.e_commerce_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class, true, true);

    @Before
    public void setUp() {
        // Perform setup actions here if needed, such as initializing mocks
        FirebaseAuth.getInstance().signOut(); // Ensure no user is signed in
    }

    @After
    public void tearDown() {
        // Perform cleanup actions here if needed
    }

    @Test
    public void testSuccessfulLogin() {
        String email = "ahnafraismahi@gmail.com";
        String password = "123456";

        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());

        // Wait for the toast message to appear
        delay(3000); // Wait for 3 seconds

        onView(withText("Login successful!!"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    // Method to introduce a delay in milliseconds
    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFailedLoginDueToIncorrectCredentials() {
        String email = "incorrect@example.com";
        String password = "wrongpassword";

        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());

        // Add a delay to allow the login process to complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check for the login failed toast message
        onView(withText("Login failed!!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyEmail() {
        String password = "password123";

        onView(withId(R.id.email)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());

        // Check for the empty email toast message
        onView(withText("Please enter email!!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyPassword() {
        String email = "test@example.com";

        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());

        // Check for the empty password toast message
        onView(withText("Please enter password!!")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }
}
