package com.example.cyrate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.cyrate.activities.IndividualUserActivity;
import com.example.cyrate.activities.IntroActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class IntroActivityTest {
    @Rule
    public ActivityTestRule<IntroActivity> activityRule = new ActivityTestRule<>(IntroActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void introActivityHasCorrectElements(){
        activityRule.launchActivity(new Intent());

        intended(hasComponent(IntroActivity.class.getName()));

        onView(withId(R.id.txt_cyrate)).check(matches(withText("CyRate")));
        onView(withId(R.id.txt_welcome)).check(matches(withText("welcome to")));
    }

}
