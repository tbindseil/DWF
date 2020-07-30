package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.main.MainComponent
import com.tjb.dwf.user.SplashScreenActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory

    fun inject(splashScreenActivity: SplashScreenActivity)
}
