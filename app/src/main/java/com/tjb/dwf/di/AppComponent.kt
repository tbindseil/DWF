package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.main.MainComponent
import com.tjb.dwf.user.SplashScreenActivity
import com.tjb.dwf.user.UserModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
// TODO can I remove UserModule and just use constructor injection
@Component(modules = [AppSubcomponents::class, UserModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory

    fun inject(splashScreenActivity: SplashScreenActivity)
}
