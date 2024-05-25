package com.example.e_commerce_app;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLoginUIElements() {
        // Launch the activity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Check if email input field is displayed
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if password input field is displayed
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if login button is displayed
        Espresso.onView(ViewMatchers.withId(R.id.login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if the "Forgot Password?" TextView is displayed
        Espresso.onView(ViewMatchers.withId(R.id.txtfpsw))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if the "Don't have an account? Sign up" TextView is displayed
        Espresso.onView(ViewMatchers.withId(R.id.txt3))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testValidLoginInput() {
        // Launch the activity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Type valid email
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("a@gmail.com"), ViewActions.closeSoftKeyboard());

        // Type valid password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.login))
                .perform(ViewActions.click());

        // Check if the progress bar is visible after clicking login
        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testInvalidLoginInput() {
        // Launch the activity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Leave email field empty and type password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("password123"), ViewActions.closeSoftKeyboard());

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.login))
                .perform(ViewActions.click());

        // Check if the progress bar is not visible after clicking login
        Espresso.onView(ViewMatchers.withId(R.id.progressBar))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
}
}