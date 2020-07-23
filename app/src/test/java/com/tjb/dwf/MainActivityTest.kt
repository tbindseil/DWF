package com.tjb.dwf;

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.rule.ActivityTestRule
import com.tjb.dwf.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainActivityTest {
    @get:Rule
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun whenNoUserInIntent_mainActivity_launchesLoginActivity() {
        Intents.init()

        // Stub the login intent to prevent LoginActivity from being displayed.
        // This helps to fix the Espresso timeout exception.
        val loginIntent = IntentMatchers.hasComponent(LoginActivity::class.java.name)
        Intents.intending(loginIntent).respondWith(ActivityResult(0, null))
        mainActivityTestRule.launchActivity(null)
        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun whenUserInIntent_mainActivity_staysOpen() {
        Intents.init()

        // TODO using intents probably isn't best. Investigate using an AppComponent scoped thing
        val startIntent = Intent()
        val dummyUser = UserPojo("f", "l", "t", 0)
        startIntent.putExtra(UserPojo.SERIALIZE_TAG, dummyUser)
        mainActivityTestRule.launchActivity(startIntent)

        // how to say, "intended matches any 0 times
        Intents.intended(IntentMatchers.anyIntent(), Intents.times(0))
        Intents.release()
    }
}
