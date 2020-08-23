package com.tjb.dwf.di

import android.content.Context
import android.content.SharedPreferences
import com.tjb.dwf.GsonModule
import com.tjb.dwf.main.MainSubcomponent
import com.tjb.dwf.storage.StorageModule
import com.tjb.dwf.user.LoginActivity
import com.tjb.dwf.user.SplashScreenActivity
import com.tjb.dwf.webclient.WebModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class, GsonModule::class, StorageModule::class, WebModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainSubcomponent.Factory

    fun inject(splashScreenActivity: SplashScreenActivity)
    fun inject(loginActivity: LoginActivity)
}
