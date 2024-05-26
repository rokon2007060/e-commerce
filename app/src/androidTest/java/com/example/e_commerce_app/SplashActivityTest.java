package com.example.e_commerce_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityScenarioRule<SplashActivity> activityRule =
            new ActivityScenarioRule<>(SplashActivity.class);

    @Test
    public void splashScreen_isDisplayed() {
        // Check that the progress bar is displayed
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

//    @Test
//    public void splashScreen_navigatesToLoginActivity() {
//        // Initialize intents recording
//        Intents.init();
//
//        // Wait for the splash delay duration + buffer time
//        try {
//            Thread.sleep(SplashActivity.SPLASH_DELAY + 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Check that the LoginActivity is started
//        intended(hasComponent(LoginActivity.class.getName()));
//
//        // Release intents recording
//        Intents.release();
//}
}
