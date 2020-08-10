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
import com.tjb.dwf.utils.NewActivityIntentFactory
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    // wtf is tag? for cancelling in queue
    private val TAG = "LOGIN_TAG:"

    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var requestQueue: RequestQueue
    @Inject
    lateinit var userController: UserController
    @Inject
    lateinit var newActivityIntentFactory: NewActivityIntentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onClickLogin(v: View?) {
        val username = (findViewById<View>(R.id.usernameText) as TextView).text.toString()
        val password = (findViewById<View>(R.id.passwordText) as TextView).text.toString()
        val responseListener = Response.Listener { response: JSONObject -> onResponse(response) }
        val errorListener = Response.ErrorListener { error: VolleyError -> onErrorResponse(error) }

        userController.login(username, password, TAG, responseListener, errorListener)
    }

    fun onClickSignUp(v: View?) {
        val username = (findViewById<View>(R.id.usernameText) as TextView).text.toString()
        val password = (findViewById<View>(R.id.passwordText) as TextView).text.toString()
        val firstName = "firstName"
        val lastName = "lastName"
        val responseListener = Response.Listener { response: JSONObject -> onResponse(response) }
        val errorListener = Response.ErrorListener { error: VolleyError -> onErrorResponse(error) }

        userController.signUp(firstName, lastName, username, password, TAG, responseListener, errorListener)
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
            val intent = newActivityIntentFactory.makeNewActivityIntent(this, MainActivity::class.java)
            intent.putExtra(UserPojo.SERIALIZE_TAG, user)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("SignUpResponseHandler", "Exception generating pojo")
            finish()
        }
    }

    private fun onErrorResponse(error: VolleyError) {
        val errorStr = error.toString()
        val intent = newActivityIntentFactory.makeNewActivityIntent(this, ErrorActivity::class.java)
        intent.putExtra(ErrorActivity.ERROR_SERIALIZATION_TAG, errorStr)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        requestQueue.cancelAll(TAG)
    }
}