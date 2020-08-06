package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.main.MainSubcomponent
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

    fun mainComponent(): MainSubcomponent.Factory

    fun inject(splashScreenActivity: SplashScreenActivity)
}
