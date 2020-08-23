package com.tjb.dwf.user

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.tjb.dwf.DWFApplication
import com.tjb.dwf.ErrorActivity
import com.tjb.dwf.R
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.utils.NewActivityIntentFactory
import org.json.JSONObject
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

    private lateinit var usernameText: TextView
    private lateinit var passwordText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as DWFApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameText = findViewById<TextView>(R.id.usernameText)
        passwordText = findViewById<TextView>(R.id.passwordText)
    }

    fun onClickLogin() {
        val username = usernameText.text.toString()
        val password = passwordText.text.toString()
        val responseListener = Response.Listener { response: JSONObject -> onResponse(response) }
        val errorListener = Response.ErrorListener { error: VolleyError -> onErrorResponse(error) }

        userController.login(username, password, TAG, responseListener, errorListener)
    }

    fun onClickSignUp() {
        val username = usernameText.text.toString()
        val password = passwordText.text.toString()
        val firstName = "firstName"
        val lastName = "lastName"
        val responseListener = Response.Listener { response: JSONObject -> onResponse(response) }
        val errorListener = Response.ErrorListener { error: VolleyError -> onErrorResponse(error) }

        userController.signUp(firstName, lastName, username, password, TAG, responseListener, errorListener)
    }

    // TODO different calls and different handlers?
    private fun onResponse(response: JSONObject) {
        try {
            // serialize user to main activity
            val intent = newActivityIntentFactory.makeNewActivityIntent(this, MainActivity::class.java)
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