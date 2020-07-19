package com.tjb.dwf.main

import android.view.ScaleGestureDetector
import com.tjb.dwf.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(scaleGestureDetector: ScaleGestureDetector): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}