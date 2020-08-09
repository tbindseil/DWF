package com.tjb.dwf.main

import com.tjb.dwf.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainSubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainSubcomponent
    }

    fun inject(mainActivity: MainActivity)
}