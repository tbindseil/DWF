package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.SplashScreenActivityTest
import com.tjb.dwf.user.SplashScreenActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class, SplashScreenActivityTest.MockUserModule::class])
interface TestAppComponent : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }

}