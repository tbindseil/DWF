package com.tjb.dwf.main

import android.view.ScaleGestureDetector
import com.tjb.dwf.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}