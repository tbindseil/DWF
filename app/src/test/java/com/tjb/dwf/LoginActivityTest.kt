package com.tjb.dwf

import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.rule.ActivityTestRule
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.tjb.dwf.di.MockUserModule
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.LoginActivity
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class LoginActivityTest {
    @get:Rule
    var loginActivityTestRule = ActivityTestRule(LoginActivity::class.java, true, true)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun onClickLogin_UserControllerIsCalled() {
        val usernameString = "usernameString"
        val passwordString = "passwordString"

        // ActivityLoginBinding.
        val usernameText = loginActivityTestRule.activity.findViewById<EditText>(R.id.usernameText)
        val passwordText = loginActivityTestRule.activity.findViewById<EditText>(R.id.passwordText)
        usernameText.setText(usernameString)
        passwordText.setText(passwordString)

        every {
            MockUserModule.getMockUserController().login(usernameString, passwordString, any(), any(), any())
        } returns Unit
        val v = mockk<View>(relaxed = true)
        loginActivityTestRule.activity.onClickLogin(v)

        verify(exactly = 1) {
            MockUserModule.getMockUserController().login(usernameString, passwordString, any(), any(), any())
        }
    }

    @Test
    fun onClickSignUp_UserControllerIsCalled() {
        val usernameString = "usernameString"
        val passwordString = "passwordString"

        // ActivityLoginBinding.
        val usernameText = loginActivityTestRule.activity.findViewById<EditText>(R.id.usernameText)
        val passwordText = loginActivityTestRule.activity.findViewById<EditText>(R.id.passwordText)
        usernameText.setText(usernameString)
        passwordText.setText(passwordString)

        every {
            MockUserModule.getMockUserController().signUp(any(), any(), usernameString, passwordString, any(), any(), any())
        } returns Unit
        val v = mockk<View>(relaxed = true)
        loginActivityTestRule.activity.onClickSignUp(v)

        verify(exactly = 1) {
            MockUserModule.getMockUserController().signUp(any(), any(), usernameString, passwordString, any(), any(), any())
        }
    }

    @Test
    fun onResponse_startsMainActivity() {
        val capturedResponseListener = slot<Response.Listener<JSONObject>>()
        every {
            MockUserModule.getMockUserController().login(
                    any(),
                    any(),
                    any(),
                    capture(capturedResponseListener),
                    any()
            )
        } returns Unit

        val v = mockk<View>(relaxed = true)
        loginActivityTestRule.activity.onClickLogin(v)

        val mockResponse = mockk<JSONObject>(relaxed = true)
        capturedResponseListener.captured.onResponse(mockResponse)
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun onErrorResponse_startsErrorActivity() {
        val capturedErrorListener = slot<Response.ErrorListener>()
        every {
            MockUserModule.getMockUserController().login(
                    any(),
                    any(),
                    any(),
                    any(),
                    capture(capturedErrorListener)
            )
        } returns Unit

        val v = mockk<View>(relaxed = true)
        loginActivityTestRule.activity.onClickLogin(v)

        val mockErrorString = "mockError"
        val mockError = mockk<VolleyError>(relaxed = true)
        every {
            mockError.toString()
        } returns mockErrorString
        capturedErrorListener.captured.onErrorResponse(mockError)
        Intents.intended(IntentMatchers.hasComponent(ErrorActivity::class.java.name))
        Intents.intended(IntentMatchers.hasExtra(ErrorActivity.ERROR_SERIALIZATION_TAG, mockErrorString))
    }

    @Test
    fun onStop_requestQueueIsCancelledWithCorrectTag() {

    }
}