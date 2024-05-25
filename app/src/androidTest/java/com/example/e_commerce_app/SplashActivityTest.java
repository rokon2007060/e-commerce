package com.example.e_commerce_app;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    // Rule to launch SplashActivity
    @Rule
    public ActivityScenarioRule<SplashActivity> activityScenarioRule = new ActivityScenarioRule<>(SplashActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void setUp() {
        idlingResource = new SimpleIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void testSplashScreenDisplayed() {
        // Check if progress bar is displayed
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));

        // Notify that splash screen loading is finished
        ((SimpleIdlingResource) idlingResource).setIdleState(true);
    }

    @Test
    public void testSplashScreenDismissed() {
        // Wait for splash screen to be dismissed
        ((SimpleIdlingResource) idlingResource).setIdleState(true);

        // Check if progress bar is not displayed
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }
}
