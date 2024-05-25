package com.example.e_commerce_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SellerActivityTest {

    @Rule
    public ActivityTestRule<SellerActivity> activityTestRule = new ActivityTestRule<>(SellerActivity.class, true, true);

    @Before
    public void setUp() {
        // Ensure no user is signed in
        FirebaseAuth.getInstance().signOut();
        // Mock signing in a user or ensure the state required for your test is set up here
    }

    @After
    public void tearDown() {
        // Clean up actions
        FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void testLoadAllProducts() {
        onView(withId(R.id.btn_all)).perform(click());
        // Add assertions to verify that all products are loaded
        // This will depend on your specific implementation, for example:
        onView(withId(R.id.recycler_view_products)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoadMedicineProducts() {
        onView(withId(R.id.btn_medicine)).perform(click());
        // Add assertions to verify that medicine products are loaded
        // This will depend on your specific implementation, for example:
        onView(withId(R.id.recycler_view_products)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoadClothingProducts() {
        onView(withId(R.id.btn_clothing)).perform(click());
        // Add assertions to verify that clothing products are loaded
        // This will depend on your specific implementation, for example:
        onView(withId(R.id.recycler_view_products)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoadElectronicsProducts() {
        onView(withId(R.id.btn_electronics)).perform(click());
        // Add assertions to verify that electronics products are loaded
        // This will depend on your specific implementation, for example:
        onView(withId(R.id.recycler_view_products)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoadBooksProducts() {
        onView(withId(R.id.btn_books)).perform(click());
        // Add assertions to verify that books products are loaded
        // This will depend on your specific implementation, for example:
        onView(withId(R.id.recycler_view_products)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogout() {
        openActionBarOverflowOrOptionsMenu(activityTestRule.getActivity());
        //onView(withText(R.string.logoutButton)).perform(click());
        // Add assertions to verify that the user is redirected to the login screen
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }
}
