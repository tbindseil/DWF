package com.tjb.dwf.di

import android.app.Activity
import android.content.Context
import com.tjb.dwf.user.IUserController
import com.tjb.dwf.user.UserController
import com.tjb.dwf.user.UserPojo
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockUserController @Inject constructor() : IUserController {
    private val mockUserController = mockk<UserController>()

    public fun getMock(): UserController {
        return mockUserController
    }

    override fun logout(context: Context) {
        return mockUserController.logout(context)
    }

    override fun getUser(activity: Activity): UserPojo? {
        return mockUserController.getUser(activity)
    }
}