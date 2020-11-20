package com.tjb.dwf

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.tjb.dwf.storage.SharedPreferencesStorage
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.UserController
import com.tjb.dwf.user.UserModel
import com.tjb.dwf.user.UserPojo
import com.tjb.dwf.utils.NewActivityIntentFactory
import com.tjb.dwf.webclient.AuthorizedJsonObjectRequest
import com.tjb.dwf.webclient.JsonRequestFactory
import com.tjb.dwf.webclient.RequestFactory
import io.mockk.*
import junit.framework.Assert.assertEquals
import org.json.JSONObject
import org.junit.Test
import java.util.HashMap

class UserControllerTest {
    // TODO relaxed for unit returning functions?
    private val gson = Gson()
    private val requestQueue = mockk<RequestQueue>(relaxed = true)
    private var userModel = UserModel()
    private val newActivityIntentFactory = mockk<NewActivityIntentFactory>(relaxed = true)
    private val jsonRequestFactory = mockk<JsonRequestFactory>(relaxed = true)
    private val sharedPreferencesStorage = mockk<SharedPreferencesStorage>(relaxed = true)
    private val requestFactory = mockk<RequestFactory>(relaxed = true)

    private val userController = UserController(
            gson,
            requestQueue,
            userModel,
            newActivityIntentFactory,
            jsonRequestFactory, // TODO  remove
            sharedPreferencesStorage,
            requestFactory
    )

    @Test
    fun onLogout_LoginActivityIsStarted_always() {
        setupUser()
        val callingActivity = mockk<Activity>(relaxed = true)

        val mockSharedPrefs = mockk<SharedPreferences>(relaxed = true)
        every {
            callingActivity.getSharedPreferences(any(), any())
        } returns mockSharedPrefs

        val mockIntentToLoginActivity = mockk<Intent>(relaxed = true)
        every {
            newActivityIntentFactory.makeNewActivityIntent(any(), LoginActivity::class.java)
        } returns mockIntentToLoginActivity

        val capturedIntent = slot<Intent>()
        every {
            callingActivity.startActivity(
                    capture(capturedIntent)
            )
        } returns Unit

        userController.logout(callingActivity)

        verify(exactly = 1) {
            callingActivity.startActivity(mockIntentToLoginActivity)
        }
    }

    @Test
    fun onLogout_tokenIsInvalidated_always() {
        val user = setupUser()
        val callingActivity = mockk<Activity>(relaxed = true)
        val mockRequest = mockk<AuthorizedJsonObjectRequest>(relaxed = true)
        every {
            requestFactory.makeLogoutRequest(user.token)
        } returns mockRequest

        userController.logout(callingActivity)

        verify(exactly = 1) {
            requestQueue.add(mockRequest)
        }
    }

    fun setupUser(): UserPojo {
        val user = createUserPojo()
        userModel.setUserPojo(user)
        return user
    }

    @Test
    fun onLogout_ArgumentActivitIsFinished_always() {
        setupUser()

        val callingActivity = mockk<Activity>(relaxed = true)
        userController.logout(callingActivity)

        verify(exactly = 1) {
            callingActivity.finish()
        }
    }

    @Test
    fun onLogout_userIsNulledInSharedPrefs_always() {
        setupUser()

        val callingActivity = mockk<Activity>(relaxed = true)
        userController.logout(callingActivity)

        verify(exactly = 1) {
            sharedPreferencesStorage.putString(UserController.USER_KEY, null)
        }
    }

    @Test
    fun onLogout_userIsNulledInUserModel_always() {
        setupUser()

        val callingActivity = mockk<Activity>(relaxed = true)
        userController.logout(callingActivity)

        assertEquals(null, userModel.getUserPojo())
    }

    @Test
    fun onLogin_CorrectRequestIsSent_always() {
        val mockRequest = mockk<JsonObjectRequest>(relaxed = true)
        every {
            jsonRequestFactory.createRequest(any(), any(), any(), any(), any(), any(), any())
        } returns mockRequest

        setupLoginCall()

        verify(exactly = 1) {
            requestQueue.add(mockRequest)
        }
    }

    @Test
    fun onLogin_controllerResponseSavesUserToSharedPrefsStorage_always() {
        val capturedControllerListener = slot<Response.Listener<JSONObject>>()
        val mockRequest = mockk<JsonObjectRequest>(relaxed = true)
        every {
            jsonRequestFactory.createRequest(
                    any(),
                    any(),
                    any(),
                    any(),
                    capture(capturedControllerListener),
                    any(),
                    any()
            )
        } returns mockRequest

        setupLoginCall()

        // inspect controller response
        val userPojo = createUserPojo()
        val userPojoString = gson.toJson(userPojo)
        val response = mockk<JSONObject>()
        every {
            response.toString()
        } returns userPojoString

        capturedControllerListener.captured.onResponse(response)

        verify {
            sharedPreferencesStorage.putString(UserController.USER_KEY, userPojoString)
        }
    }

    @Test
    fun onSignup_CorrectRequestIsSent_always() {
        val mockRequest = mockk<JsonObjectRequest>(relaxed = true)
        every {
            jsonRequestFactory.createRequest(any(), any(), any(), any(), any(), any(), any())
        } returns mockRequest

        setupSignupCall()

        verify(exactly = 1) {
            requestQueue.add(mockRequest)
        }
    }

    @Test
    fun onSignup_controllerResponseSavesUserToSharedPrefsStorage_always() {
        val capturedControllerListener = slot<Response.Listener<JSONObject>>()
        val mockRequest = mockk<JsonObjectRequest>(relaxed = true)
        every {
            jsonRequestFactory.createRequest(
                    any(),
                    any(),
                    any(),
                    any(),
                    capture(capturedControllerListener),
                    any(),
                    any()
            )
        } returns mockRequest

        setupSignupCall()

        // inspect controller response
        val userPojo = createUserPojo()
        val userPojoString = gson.toJson(userPojo)
        val response = mockk<JSONObject>()
        every {
            response.toString()
        } returns userPojoString

        capturedControllerListener.captured.onResponse(response)

        verify {
            sharedPreferencesStorage.putString(UserController.USER_KEY, userPojoString)
        }
    }

    @Test
    fun onGetUser_userIsReturnedFromModel_whenNotNull() {
        val expectedUserPojo = createUserPojo()
        userModel.setUserPojo(expectedUserPojo)

        val observedUserPojo = userController.getUser()

        assertEquals(expectedUserPojo, observedUserPojo)
    }

    @Test
    fun onGetUser_userIsReturnedFromSharedPrefs_whenNullInModel() {
        val expectedUserPojo = createUserPojo()
        val expectedUserPojoString = gson.toJson(expectedUserPojo)

        every {
            sharedPreferencesStorage.getString(eq(UserController.USER_KEY), any())
        } returns expectedUserPojoString

        val observedUserPojo = userController.getUser()

        // make sure user controller returns whats in the model
        assertEquals(observedUserPojo, userModel.getUserPojo())
        // make sure user controller returns what we expect
        assertEquals(expectedUserPojo, observedUserPojo)
    }

    private fun createUserPojo(): UserPojo {
        return UserPojo("u", "t", 0)
    }

    private fun setupSignupCall() {
        val url = "https://draw-n-stuff.com/auth/register"
        val username = "username"
        val password = "password"
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password
        val responseListener = Response.Listener { response: JSONObject ->  }
        val errorListener = Response.ErrorListener { error: VolleyError -> }
        val tag = "tag"

        userController.signUp(username, password, tag, responseListener, errorListener)
        verify(exactly = 1) {
            jsonRequestFactory.createRequest(
                    Request.Method.POST,
                    eq(url),
                    eq(params),
                    eq(responseListener),
                    any(), // this is generated in the method, could be problematic
                    eq(errorListener),
                    eq(tag)
            )
        }
    }

    private fun setupLoginCall() {
        val url = "https://draw-n-stuff.com/auth/login"
        val username = "username"
        val password = "password"
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password
        val activityResponseListener = Response.Listener { response: JSONObject ->  }
        val errorListener = Response.ErrorListener { error: VolleyError -> }
        val tag = "tag"

        userController.login(username, password, tag, activityResponseListener, errorListener)

        verify(exactly = 1) {
            jsonRequestFactory.createRequest(
                    Request.Method.POST,
                    eq(url),
                    eq(params),
                    eq(activityResponseListener),
                    any(), // this is generated in the method, tested below
                    eq(errorListener),
                    eq(tag)
            )
        }
    }
}
