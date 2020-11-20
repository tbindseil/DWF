package com.tjb.dwf;

import android.os.Build
import android.view.View
import androidx.test.rule.ActivityTestRule
import com.tjb.dwf.di.MockPinchGestureListener
import com.tjb.dwf.di.MockUserModule
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.UserPojo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
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

    @Before
    fun setup() {
        every {
            MockPinchGestureListener.getMockPinchGestureListener().installMainActivity(any())
        } returns Unit
        val user = createUserPojo()
        every {
            MockUserModule.getMockUserController().getUser()
        } returns user
        mainActivityTestRule.launchActivity(null)
    }

    @Test
    fun whenOnClickLogout_userControllerLogout_isCalled() {
        every {
            MockUserModule.getMockUserController().logout(any())
        } returns Unit
        val v = mockk<View>(relaxed = true)
        mainActivityTestRule.activity.onClickLogout(v)

        verify(exactly = 1) {
            MockUserModule.getMockUserController().logout(mainActivityTestRule.activity)
        }
    }

    @Test
    fun onCreate_pinchGestureListener_installCalled() {
        verify(exactly = 1) {
            MockPinchGestureListener.getMockPinchGestureListener().installMainActivity(mainActivityTestRule.activity)
        }
    }

    private fun createUserPojo(): UserPojo {
        return UserPojo("u", "t", 0)
    }
}
