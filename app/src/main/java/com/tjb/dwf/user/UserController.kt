package com.tjb.dwf.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserController @Inject constructor(private val gson: Gson) {
    private val USER_KEY = "USER"

    private var userPojo: UserPojo? = null

    fun logout(activity: Activity) {
        userPojo = null

        val sharedPref: SharedPreferences = activity.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_KEY, null);
        editor.commit();

        val intent: Intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun getUser(activity: Activity): UserPojo? {
        if (userPojo != null) {
            return userPojo
        }

        // check for token in storage
        val sharedPref: SharedPreferences = activity.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE)
        val userJson = sharedPref.getString(USER_KEY, null)
        userPojo = gson.fromJson(userJson, UserPojo::class.java)

        // TODO Maybe I should check for more than just null, like call to check authentication
        if (userPojo == null) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)

            // typically this breaks back stack functionality, but here we don't want that
            activity.finish()
        }

        Log.e("Question", "does this get called?")

        return userPojo
    }
}