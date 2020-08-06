package com.tjb.dwf;

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.rule.ActivityTestRule
import com.tjb.dwf.di.MockPinchGestureReceiver
import com.tjb.dwf.di.MockUserModule
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.UserPojo
import io.mockk.every
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MainActivityTest {
    @get:Rule
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setup() {
        every {
            MockPinchGestureReceiver.getMockPinchGestureReceiver().installMainActivity(any())
        } returns Unit
        mainActivityTestRule.launchActivity(null)
    }

    @Test
    fun whenOnClickLogout_userControllerLogout_isCalled() {
        every {
            MockUserModule.getMockUserController().logout(any())
        } returns Unit
        mainActivityTestRule.activity.onClickLogout()

        verify(exactly = 1) {
            MockUserModule.getMockUserController().logout(mainActivityTestRule.activity)
        }
    }

    @Test
    fun onCreate_pinchGestureReceiver_installCalled() {
        verify(exactly = 1) {
            MockPinchGestureReceiver.getMockPinchGestureReceiver().installMainActivity(mainActivityTestRule.activity)
        }
    }
}
