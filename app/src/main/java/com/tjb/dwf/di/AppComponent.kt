package com.tjb.dwf.di

import android.content.Context
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.modules.PinchProviders
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PinchProviders::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}
