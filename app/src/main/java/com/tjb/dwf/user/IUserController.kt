package com.tjb.dwf.user

import android.app.Activity
import android.content.Context

interface IUserController {
    fun logout(context: Context)
    fun getUser(activity: Activity): UserPojo?
}