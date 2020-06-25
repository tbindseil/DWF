package com.tjb.dwf;

import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void whenNoUserInIntent_mainActivity_launchesLoginActivity() {
        Intents.init();

        // Stub the login intent to prevent LoginActivity from being displayed.
        // This helps to fix the Espresso timeout exception.
        Matcher<Intent> loginIntent = hasComponent(LoginActivity.class.getName());
        intending(loginIntent).respondWith(new Instrumentation.ActivityResult(0, null));

        mainActivityTestRule.launchActivity(null);
        intended(hasComponent(LoginActivity.class.getName()));

        Intents.release();
    }
}
