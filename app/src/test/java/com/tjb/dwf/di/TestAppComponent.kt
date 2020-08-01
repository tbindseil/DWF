package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.SplashScreenActivityTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SplashScreenActivityTest.MockUserModule::class, AppSubcomponents::class])
interface TestAppComponent : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }

}