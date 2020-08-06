package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.main.MainSubcomponent
import com.tjb.dwf.main.PinchGestureReceiver
import com.tjb.dwf.user.UserController
import dagger.*
import io.mockk.mockk
import javax.inject.Singleton

@Singleton
@Component(modules = [MockAppSubcomponents::class, MockUserModule::class])
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
@Subcomponent(modules = [MockPinchGestureReceiver::class])
interface MockMainSubcomponent: MainSubcomponent {
    @Subcomponent.Factory
    interface Factory: MainSubcomponent.Factory {
        override fun create(): MockMainSubcomponent
    }
}

@Module
class MockPinchGestureReceiver {
    companion object {
        private val mockPinchGestureReceiver = mockk<PinchGestureReceiver>()

        fun getMockPinchGestureReceiver(): PinchGestureReceiver {
            return mockPinchGestureReceiver
        }
    }

    @ActivityScope
    @Provides
    fun providesPinchGestureReceiver(): PinchGestureReceiver {
        return mockPinchGestureReceiver
    }
}
