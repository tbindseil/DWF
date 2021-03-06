package com.tjb.dwf.user

import android.app.Activity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.google.gson.Gson
import com.tjb.dwf.storage.Storage
import com.tjb.dwf.utils.NewActivityIntentFactory
import com.tjb.dwf.webclient.JsonRequestFactory
import com.tjb.dwf.webclient.RequestFactory
import org.json.JSONObject
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserController @Inject constructor(private val gson: Gson,
                                         private val requestQueue: RequestQueue,
                                         private var userModel: UserModel,
                                         private val newActivityIntentFactory: NewActivityIntentFactory,
                                         private val jsonRequestFactory: JsonRequestFactory,
                                         private val sharedPreferencesStorage: Storage,
                                         private val requestFactory: RequestFactory) {

    companion object {
        val USER_KEY = "USER"
    }

    fun logout(activity: Activity) {
        val request = requestFactory.makeLogoutRequest(userModel.getUserPojo()!!.token)

        requestQueue.add(request)

        userModel.setUserPojo(null)

        sharedPreferencesStorage.putString(USER_KEY, null)

        val intent = newActivityIntentFactory.makeNewActivityIntent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun login(username: String,
              password: String,
              tag: String,
              activityResponseListener: Response.Listener<JSONObject>,
              errorListener: Response.ErrorListener) {
        val url = "https://draw-n-stuff.com/auth/login"
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password

        // be sure to save user token upon login in addition to whatever client wants done on login
        val controllerResponseListener = Response.Listener { response: JSONObject ->
            saveUser(response)
        }

        // maybe install handlers once? like a handler is a pair of url and onResponse?,

        // activities have to wait for their own calls to finish TODO
        val request = jsonRequestFactory.createRequest(
                Request.Method.POST,
                url,
                params,
                activityResponseListener,
                controllerResponseListener,
                errorListener,
                tag
        )
        requestQueue.add(request)
    }

    fun signUp(username: String,
               password:String,
               tag: String,
               activityResponseListener: Response.Listener<JSONObject>,
               errorListener: Response.ErrorListener) {
        val url = "https://draw-n-stuff.com/auth/register"
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password

        // be sure to save user token upon login in addition to whatever client wants done on login
        val controllerResponseListener = Response.Listener { response: JSONObject ->
            saveUser(response)
        }

        val request = jsonRequestFactory.createRequest(
                Request.Method.POST,
                url,
                params,
                activityResponseListener,
                controllerResponseListener,
                errorListener,
                tag
        )
        request.tag = tag
        requestQueue.add(request)
    }

    private fun saveUser(response: JSONObject) {
        val user = gson.fromJson(response.toString(), UserPojo::class.java)
        userModel.setUserPojo(user)

        val userJson = gson.toJson(user)
        sharedPreferencesStorage.putString(USER_KEY, userJson)
    }

    fun getUser(): UserPojo? {
        if (userModel.getUserPojo() != null) {
            return userModel.getUserPojo()
        }

        // check for token in storage
        val userJson = sharedPreferencesStorage.getString(USER_KEY, null)
        userModel.setUserPojo(gson.fromJson(userJson, UserPojo::class.java))

        // note: can return null here, meaning user is logged out
        return userModel.getUserPojo()
    }
}
