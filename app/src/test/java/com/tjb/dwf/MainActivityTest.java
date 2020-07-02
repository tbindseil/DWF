package com.tjb.dwf;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
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

    @Test
    public void whenUserInIntent_mainActivity_staysOpen() {
        Intents.init();

        Intent startIntent = new Intent();
        UserPojo dummyUser = new UserPojo("f", "l", "t", 0);
        startIntent.putExtra(UserPojo.SERIALIZE_TAG, dummyUser);
        mainActivityTestRule.launchActivity(startIntent);

        // how to say, "intended matches any 0 times
        intended(anyIntent(), times(0));

        Intents.release();
    }
}
