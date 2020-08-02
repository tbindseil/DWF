package com.tjb.dwf

import android.os.Build
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.SplashScreenActivity
import com.tjb.dwf.user.UserController
import com.tjb.dwf.user.UserPojo
import dagger.Module
import dagger.Provides
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SplashScreenActivityTest {

    // TODO scenario rule instead? seems newer
    @get:Rule
    var splashScreenActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java, true, false)

    @Module
    class MockUserModule {
        companion object {
            private val mockUserController = mockk<UserController>()

            fun getMockUserController(): UserController {
                return mockUserController
            }
        }

        @Singleton
        @Provides
        fun providesMockUserController(): UserController {
            return mockUserController
        }
    }

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun whenNoUserInStorage_splashScreenActivity_launchesLoginActivity() {
        every { MockUserModule.getMockUserController().getUser(any()) } returns null

        splashScreenActivityTestRule.launchActivity(null)

        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun whenUserInStorage_splashScreenActivity_launchesMainActivity() {
        val dummy = UserPojo("f", "l", "t", 0);
        every { MockUserModule.getMockUserController().getUser(any()) } returns dummy

        splashScreenActivityTestRule.launchActivity(null)

        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }
}
