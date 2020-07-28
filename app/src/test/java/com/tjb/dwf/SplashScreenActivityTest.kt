package com.tjb.dwf

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import android.view.ScaleGestureDetector
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.rule.ActivityTestRule
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.UserController
import com.tjb.dwf.user.UserPojo
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SplashScreenActivityTest {
    @get:Rule
    var splashScreenActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java, true, false)

    private val userController = mockk<UserController>()

    @Test
    fun whenNoUserInStorage_splashScreenActivity_launchesLoginActivity() {
        Intents.init()

        every { userController.getUser(any()) } returns null
        splashScreenActivityTestRule.activity.userController = userController;

        splashScreenActivityTestRule.launchActivity(null)

        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
        Intents.release()
    }

    // so I think the issue is that I need to make that fake app config

    @Test
    fun whenUserInStorage_splashScreenActivity_launchesMainActivity() {
        Intents.init()
        splashScreenActivityTestRule.launchActivity(null)

        val dummy = UserPojo("f", "l", "t", 0);
        every { userController.getUser(any()) } returns dummy
        splashScreenActivityTestRule.activity.userController = userController;

        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        Intents.release()
    }
}
