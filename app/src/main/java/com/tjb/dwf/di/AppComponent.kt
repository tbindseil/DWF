package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.GsonModule
import com.tjb.dwf.main.MainSubcomponent
import com.tjb.dwf.user.SplashScreenActivity
import com.tjb.dwf.webclient.WebModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class, GsonModule::class, WebModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainSubcomponent.Factory

    fun inject(splashScreenActivity: SplashScreenActivity)
}
