package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.user.UserController
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class, MockUserModule::class])
interface MockAppComponent : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MockAppComponent
    }

}


@Module
class MockUserModule {
    companion object {
        private val mockUserController = mockk<UserController>()

        fun getMockUserController(): UserController {
            return mockUserController
        }
    }

    @Singleton
    @Provides
    fun providesMockUserController(): UserController {
        return mockUserController
    }
}
