package com.tjb.dwf.storage

import android.content.Context
import android.content.SharedPreferences
import com.tjb.dwf.user.UserPojo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesStorage @Inject constructor(private val context: Context) : Storage {

    override fun putString(key: String, value: String?) {
        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value);
        editor.commit();
    }

    override fun getString(key: String, defaultValue: String?): String? {

        val sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val returnValue = sharedPref.getString(key, defaultValue)

        return returnValue
    }
}