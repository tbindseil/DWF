package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.GsonModule
import com.tjb.dwf.main.MainModule
import com.tjb.dwf.main.MainSubcomponent
import com.tjb.dwf.main.PinchGestureListener
import com.tjb.dwf.user.UserController
import com.tjb.dwf.webclient.WebModule
import dagger.*
import io.mockk.mockk
import javax.inject.Singleton

@Singleton
@Component(modules = [MockAppSubcomponents::class,
    MockUserModule::class,
    GsonModule::class,
    WebModule::class])
interface MockAppComponent : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MockAppComponent
    }

    override fun mainComponent(): MockMainSubcomponent.Factory
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

@Module(subcomponents = [MockMainSubcomponent::class])
class MockAppSubcomponents {
}

@ActivityScope
@Subcomponent(modules = [MockPinchGestureListener::class, MainModule::class])
interface MockMainSubcomponent: MainSubcomponent {
    @Subcomponent.Factory
    interface Factory: MainSubcomponent.Factory {
        override fun create(): MockMainSubcomponent
    }
}

@Module
class MockPinchGestureListener {
    companion object {
        private val mockPinchGestureListener = mockk<PinchGestureListener>()

        fun getMockPinchGestureListener(): PinchGestureListener {
            return mockPinchGestureListener
        }
    }

    @ActivityScope
    @Provides
    fun providesPinchGestureListener(): PinchGestureListener {
        return mockPinchGestureListener
    }
}
