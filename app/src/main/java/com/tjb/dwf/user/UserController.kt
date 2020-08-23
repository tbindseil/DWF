package com.tjb.dwf.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.tjb.dwf.storage.Storage
import com.tjb.dwf.utils.NewActivityIntentFactory
import com.tjb.dwf.webclient.JsonRequestFactory
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
                                         private val sharedPreferencesStorage: Storage) {
                                         // TODO investigate fan in / fan out stuff

    companion object {
        val USER_KEY = "USER"
    }

    fun logout(activity: Activity) {
        userModel.setUserPojo(null)

        sharedPreferencesStorage.putString(USER_KEY, null)

        // sounds like I could start login activity from application context (make it static in dwfapplication)
        // this would actually create a new task, and I think eliminate back pop of activities, which is good here
        // lastly, it would remove the calling of activity here, but that could be hard to test
        val intent = newActivityIntentFactory.makeNewActivityIntent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun login(username: String,
              password: String,
              tag: String,
              activityResponseListener: Response.Listener<JSONObject>,
              errorListener: Response.ErrorListener) {
        val url = "https://draw-n-stuff.com/users/authenticate"
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password

        // be sure to save user token upon login in addition to whatever client wants done on login
        val controllerResponseListener = Response.Listener { response: JSONObject ->
            val user = gson.fromJson(response.toString(), UserPojo::class.java)
            val userJson = gson.toJson(user)
            sharedPreferencesStorage.putString(USER_KEY, userJson)
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

    fun signUp(firstName: String,
               lastName: String,
               username: String,
               password:String,
               tag: String,
               activityResponseListener: Response.Listener<JSONObject>,
               errorListener: Response.ErrorListener) {
        val url = "https://draw-n-stuff.com/users/register"
        val params: MutableMap<String?, String?> = HashMap()
        params["firstName"] = firstName
        params["lastName"] = lastName
        params["username"] = username
        params["password"] = password

        // be sure to save user token upon login in addition to whatever client wants done on login
        val controllerResponseListener = Response.Listener { response: JSONObject ->
            val user = gson.fromJson(response.toString(), UserPojo::class.java)
            val userJson = gson.toJson(user)
            sharedPreferencesStorage.putString(USER_KEY, userJson)
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
        val userJson = gson.toJson(user)

        sharedPreferencesStorage.putString(USER_KEY, userJson)
    }

    fun getUser(activity: Activity): UserPojo? {
        if (userModel.getUserPojo() != null) {
            // TODO how to test, say, given that userPojo is null, getUser returns user pojo
            // do I need another layer in order to mock what otherLayer.getUserPojo returns?
            // maybe, that might be the magical settings layer from scorpio
            // should I use that?
            // what would it look like?
            // new class somewhere (probably top level app component) SettingsRegistry
            // then, upon constructor in UserController, all settings are registered with SettingsRegistry
            // upon registration, each setting is given a callback
            // so when userpojo is adjusted, settings call back happens
            // settings call back in this case updates sharedprefs
            // now i can mock what settings registry returns in order to test what happens when user pojo is null
            //
            // so idk if upon construction is best time
            // which means some other entity has to call settingsmanager.register(usercontroller) or .register(userpojo)
            // or, I create usercontroller with a mock and vefify that it calls settingsmanager.register(userpojo, USER_KEY)
            // why does userpojo need to be registered? so it can be managed with user key
            // maybe a setting is a key and a value (generic), this same setting class manages the callback
            // and the call back is set during register. ok, I think I get it,

            // so usercontroller always talks to user model, asks for user pojo
            // upon constructor, usercontroller registers usermodel with settingsregistry
            // then settings registry will update the pojo asynchronously? Is that even necessary? not really
            // sooo what does settings registry do to make it synchronous

            // shit, i'm mixing up settings and user authentication

            // on update takes lock
            //
            // first, put this thing in a ~~setting~~ model
            return userModel.getUserPojo()
        }

        // check for token in storage
        val userJson = sharedPreferencesStorage.getString(USER_KEY, null)
        println("userjson is: ")
        println(userJson)
        userModel.setUserPojo(gson.fromJson(userJson, UserPojo::class.java))

        // note: can return null here, meaning user is logged out
        return userModel.getUserPojo()
    }
}
