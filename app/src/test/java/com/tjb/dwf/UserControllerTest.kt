package com.tjb.dwf

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.UserController
import com.tjb.dwf.user.UserModel
import com.tjb.dwf.user.UserPojo
import com.tjb.dwf.utils.NewActivityIntentFactory
import com.tjb.dwf.webclient.JsonRequestFactory
import io.mockk.*
import junit.framework.Assert.assertEquals
import org.json.JSONObject
import org.junit.Test
import java.util.HashMap

class UserControllerTest {
    // TODO relaxed for unit returning functions?
    private val gson = mockk<Gson>(relaxed = true)
    private val requestQueue = mockk<RequestQueue>(relaxed = true)
    private var userModel = UserModel()
    private val newActivityIntentFactory = mockk<NewActivityIntentFactory>(relaxed = true)
    private val jsonRequestFactory = mockk<JsonRequestFactory>(relaxed = true)

    private val userController = UserController(
            gson,
            requestQueue,
            userModel,
            newActivityIntentFactory,
            jsonRequestFactory
    )

    @Test
    fun onLogout_LoginActivityIsStarted_always() {
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
    fun onLogout_ArgumentActivitIsFinished_always() {
        val callingActivity = mockk<Activity>(relaxed = true)
        userController.logout(callingActivity)

        verify(exactly = 1) {
            callingActivity.finish()
        }
    }

    @Test
    fun onLogout_userIsNulledInSharedPrefs_always() {
        val callingActivity = mockk<Activity>(relaxed = true)

        val mockSharedPrefs = mockk<SharedPreferences>(relaxed = true)
        every {
            callingActivity.getSharedPreferences(any(), any())
        } returns mockSharedPrefs

        val mockEditor = mockk<SharedPreferences.Editor>(relaxed = true)
        every {
            mockSharedPrefs.edit()
        } returns mockEditor

        userController.logout(callingActivity)

        verify(exactly = 1) {
            mockEditor.putString(any(), null);
            mockEditor.commit();
        }
    }

    @Test
    fun onLogout_userIsNulledInUserModel_always() {
        val callingActivity = mockk<Activity>(relaxed = true)
        userController.logout(callingActivity)

        assertEquals(null, userModel.getUserPojo())
    }

    @Test
    fun onLogin_CorrectRequestIsSent_always() {
        val url = "https://draw-n-stuff.com/users/authenticate"
        val username = "username"
        val password = "password"
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password
        val responseListener = Response.Listener { response: JSONObject ->  }
        val errorListener = Response.ErrorListener { error: VolleyError -> }
        val tag = "tag"

        val mockRequest = mockk<JsonObjectRequest>(relaxed = true)
        every {
            jsonRequestFactory.createRequest(any(), any(), any(), any(), any(), any())
        } returns mockRequest

        userController.login(username, password, tag, responseListener, errorListener)

        verify(exactly = 1) {
            jsonRequestFactory.createRequest(
                    Request.Method.POST,
                    eq(url),
                    eq(params),
                    eq(responseListener),
                    eq(errorListener),
                    eq(tag)
            )
        }

        verify(exactly = 1) {
            requestQueue.add(mockRequest)
        }
    }

    @Test
    fun onSignup_CorrectRequestIsSent_always() {
        val firstName = "firstName"
        val lastName = "lastName"
        val username = "username"
        val password = "password"
        val url = "https://draw-n-stuff.com/users/register"
        val params: MutableMap<String?, String?> = HashMap()
        params["firstName"] = firstName
        params["lastName"] = lastName
        params["username"] = username
        params["password"] = password

        val responseListener = Response.Listener { response: JSONObject ->  }
        val errorListener = Response.ErrorListener { error: VolleyError -> }
        val tag = "tag"

        val mockRequest = mockk<JsonObjectRequest>(relaxed = true)
        every {
            jsonRequestFactory.createRequest(any(), any(), any(), any(), any(), any())
        } returns mockRequest

        userController.signUp(firstName, lastName, username, password, tag, responseListener, errorListener)

        verify(exactly = 1) {
            jsonRequestFactory.createRequest(
                    Request.Method.POST,
                    eq(url),
                    eq(params),
                    eq(responseListener),
                    eq(errorListener),
                    eq(tag)
            )
        }

        verify(exactly = 1) {
            requestQueue.add(mockRequest)
        }
    }

    private fun createUserPojo(): UserPojo {
        return UserPojo("f", "l", "t", 0)
    }

    @Test
    fun onGetUser_userIsReturnedFromModel_whenNotNull() {
        val callingActivity = mockk<Activity>()

        val expectedUserPojo = createUserPojo()
        userModel.setUserPojo(expectedUserPojo)

        val observedUserPojo = userController.getUser(callingActivity)

        assertEquals(expectedUserPojo, observedUserPojo)
    }

    @Test
    fun onGetUser_userIsReturnedFromSharedPrefs_whenNullInModel() {
        val callingActivity = mockk<Activity>()

        val expectedUserPojo = createUserPojo()

        val mockSharedPreferences = mockk<SharedPreferences>(relaxed = true)
        every {
            callingActivity.getSharedPreferences(any(), any())
        } returns mockSharedPreferences

        val jsonUserString = "jsonUserString"
        every {
            mockSharedPreferences.getString("USER", null)
        } returns jsonUserString

        every {
            gson.fromJson(jsonUserString, UserPojo::class.java)
        } returns expectedUserPojo

        val observedUserPojo = userController.getUser(callingActivity)

        assertEquals(observedUserPojo, userModel.getUserPojo())
        assertEquals(expectedUserPojo, observedUserPojo)
    }
}
