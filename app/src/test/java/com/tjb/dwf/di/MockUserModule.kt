package com.tjb.dwf.di

import com.tjb.dwf.user.UserController
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class MockUserModule {

    val mockUserController = mockk<UserController>()

    @Singleton
    @Provides
    fun providesMockUserController(): UserController {
        return mockUserController
    }
}

