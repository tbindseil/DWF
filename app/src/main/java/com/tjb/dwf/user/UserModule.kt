package com.tjb.dwf.user

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// TODO try to remove
@Module
class UserModule {
    @Singleton
    @Provides // binds?
    fun providesUserController(): UserController {
        return UserController()
    }
}