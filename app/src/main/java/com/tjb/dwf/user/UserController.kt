package com.tjb.dwf.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.tjb.dwf.GsonSingleton
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class UserController /*@Inject constructor()*/ : IUserController {
    private val USER_KEY = "USER"

    private var userPojo: UserPojo? = null

    override fun logout(context: Context) {
        userPojo = null

        val sharedPref: SharedPreferences = context.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_KEY, null);
        editor.commit();
    }

    override fun getUser(activity: Activity): UserPojo? {
        if (userPojo != null) {
            return userPojo
        }

        val result: Serializable? = activity.getIntent().getSerializableExtra(UserPojo.SERIALIZE_TAG)
        userPojo = if (result is UserPojo) result else null
        if (userPojo != null) { // maybe encapsulate this?
            return userPojo
        }

        // check for token in storage
        val sharedPref: SharedPreferences = activity.getSharedPreferences(USER_KEY, Context.MODE_PRIVATE)
        val userJson = sharedPref.getString(USER_KEY, null)
        // TODO beanify gson
        userPojo = GsonSingleton.getInstance().fromJson(userJson, UserPojo::class.java)
        // Maybe I should check for more than just null
        if (userPojo == null) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)

            // typically this breaks back stack functionality, but here we don't want that here
            activity.finish()
        }

        Log.e("Question", "does this get called?")

        return userPojo
    }
}