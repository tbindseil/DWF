package com.tjb.dwf.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserModel @Inject constructor() {

    private var userPojo: UserPojo? = null

    fun setUserPojo(userPojo: UserPojo?) {
        this.userPojo = userPojo
    }

    fun getUserPojo(): UserPojo? {
        return userPojo
    }
}