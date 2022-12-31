package com.example.cyrate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.cyrate.activities.FavoritesActivity;
import com.example.cyrate.activities.IndividualUserActivity;
import com.example.cyrate.activities.UserListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// Mock the RequestServerForService class
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class UserListActivityTest {
    @Rule
    public ActivityTestRule<UserListActivity> activityRule = new ActivityTestRule<>(UserListActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void selectingUserNavigateToIndividualUser(){
        activityRule.launchActivity(new Intent());

        onView(withId(R.id.userList_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //verify we navigate to the individual user activity
        intended(hasComponent(IndividualUserActivity.class.getName()));

        //verify the email matches
        onView(withId(R.id.user_email)).check(matches(withText("anbu3@gmail.com")));
    }

    @Test
    public void openNavMenu(){
        //verify we can open the nav menu from here
        onView(withId(R.id.open_menu_icon)).perform(click());
        onView(withId(R.id.nav_menu_header)).check(matches(isDisplayed()));
    }

    public void navigateToFavoritesList(){

        //open menu, click favorites
        onView(withId(R.id.open_menu_icon)).perform(click());
        onView(withId(R.id.nav_menu_header)).check(matches(isDisplayed()));
        onView(withId(R.id.nav_favorites)).perform(click());

        intended(hasComponent(FavoritesActivity.class.getName()));
    }

}

