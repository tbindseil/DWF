package com.tjb.dwf

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerTestAppComponent
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.SplashScreenActivity
import com.tjb.dwf.user.UserController
import com.tjb.dwf.user.UserPojo
import dagger.Module
import dagger.Provides
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Singleton

//@RunWith(RobolectricTestRunner::class)
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SplashScreenActivityTest {

    //private val mockUserController = mockk<UserController>()

    // TODO scenario rule instead? seems newer
    //@get:Rule
    //var splashScreenActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java, true, false)

    @Module
    class MockUserModule {
        //static val mockUserController = mockk<UserController>()
        companion object {
            val mockUserController = mockk<UserController>()
        }

        @Singleton
        @Provides
        fun providesMockUserController(): UserController {
            return mockUserController
        }
    }

    @Before
    fun setup() {
    }

    //@get:Rule
    //var splashScreenActivityScenarioRule = ActivityScenarioRule(SplashScreenActivity::class.java)


    /*@Before
    fun setup() {
        val scenario = splashScreenActivityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.INITIALIZED)
        scenario.result.
        /*val scenario = launchActivity<SplashScreenActivity>()
        scenario.moveToState(Lifecycle.State.INITIALIZED)
        scenario.ac*/
    }*/

    // i htink thie issue is with var/val I want to try to have the test application that overrides initialize
    // i should be able to get this to work now that i have figured out the issue with the modulebeing an inner class

    @get:Rule
    var splashScreenActivityTestRule = object : ActivityTestRule<SplashScreenActivity>(SplashScreenActivity::class.java, true, false) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val context = activity.applicationContext
            val testAppComponent = DaggerTestAppComponent.factory().create(context)
            (activity.application as DWFApplication).setTestAppComponent(testAppComponent)
        }
    }

    @Test
    fun whenNoUserInStorage_splashScreenActivity_launchesLoginActivity() {
        Intents.init() // TODO @Before

        every { MockUserModule.mockUserController.getUser(any()) } returns null

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
        every { MockUserModule.mockUserController.getUser(any()) } returns dummy

        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        Intents.release()
    }
}
