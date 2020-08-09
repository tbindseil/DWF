package com.tjb.dwf.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.tjb.dwf.ErrorActivity
import com.tjb.dwf.R
import com.tjb.dwf.main.MainActivity
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private val TAG = "LOGIN_TAG:"

    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onClickLogin(v: View?) {
        val username = (findViewById<View>(R.id.usernameText) as TextView).text.toString()
        val password = (findViewById<View>(R.id.passwordText) as TextView).text.toString()
        val params: MutableMap<String?, String?> = HashMap()
        params["username"] = username
        params["password"] = password
        val parameters = JSONObject(params as Map<*, *>)
        val url = "https://draw-n-stuff.com/users/authenticate"
        val responseListener = Response.Listener { response: JSONObject -> onResponse(response) }
        val errorListener = Response.ErrorListener { error: VolleyError -> onErrorResponse(error) }

        // activities have to wait for their own calls to finish TODO
        val request: Request<JSONObject> = JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener)
        //RequestQueueSingleton.getInstance().add(request, TAG) // TODO tag part of abstract factory factory method
        request.setTag(TAG)
        requestQueue.add(request)
    }

    fun onClickSignUp(v: View?) {
        val username = (findViewById<View>(R.id.usernameText) as TextView).text.toString()
        val password = (findViewById<View>(R.id.passwordText) as TextView).text.toString()
        val firstName = "firstName"
        val lastName = "lastName"
        val params: MutableMap<String?, String?> = HashMap()
        params["firstName"] = firstName
        params["lastName"] = lastName
        params["username"] = username
        params["password"] = password
        val parameters = JSONObject(params as Map<*, *>)
        val url = "https://draw-n-stuff.com/users/register"
        val responseListener = Response.Listener { response: JSONObject -> onResponse(response) }
        val errorListener = Response.ErrorListener { error: VolleyError -> onErrorResponse(error) }

        // TODO JsonObjectRequest factory? builder? prototype? flyweight?
        // builder pro, can add in authentication headers independently
        //         con, have to add in authentication headers independently
        // factory pro, can ask for authenticated request, or request prototype
        //         con, have to make new factory method whenever a new request prototype is made
        //         so, what other request functionalities do i need
        //              authenticated
        //              parameters
        //              response
        //         maybe this is when I look into the smithy model thing
        //         Or I could just keep on rolling... ie have an object family (abstract factory) that
        //         does make request, make response
        //         ehhh but idk if I really like that because I feel like each call to makeRequest
        //         would require the parameters as a pojo. So I have to make pojos + request + response
        //         per api/action/interaction with the backend
        //         then the factory would be something like
        //         actually, I kinda like that, use gson to go between pojo and json
        //         so, one abstractfactory, then one concrete factory for each api
        //         each concrete factory has make request, make response handler, make error handler
        val request: Request<JSONObject> = JsonObjectRequest(Request.Method.POST, url, parameters, responseListener, errorListener)
        request.setTag(TAG)
        requestQueue.add(request)
    }

    // TODO different calls and different handlers?
    private fun onResponse(response: JSONObject) {
        try {
            // save User
            val user = gson.fromJson(response.toString(), UserPojo::class.java)
            val sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            val userJson = gson.toJson(user)
            editor.putString("USER", userJson)
            editor.commit()

            // serialize user to main activity
            val intent = Intent(this.applicationContext, MainActivity::class.java)
            intent.putExtra(UserPojo.SERIALIZE_TAG, user)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("SignUpResponseHandler", "Exception generating pojo")
            finish()
        }
    }

    private fun onErrorResponse(error: VolleyError) {
        val errorStr = error.toString()
        val intent = Intent(this.applicationContext, ErrorActivity::class.java)
        intent.putExtra(ErrorActivity.ERROR_SERIALIZATION_TAG, errorStr)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        requestQueue.cancelAll(TAG)
    }
}