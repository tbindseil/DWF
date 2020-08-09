package com.tjb.dwf.main

import android.content.Context
import android.view.ScaleGestureDetector
import com.tjb.dwf.di.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainModule { // could be named better

    // import javax.inject.Inject

    @ActivityScope
    @Provides
    fun providesScaleGestureDetector(context: Context, pinchGestureListener: PinchGestureListener):
            ScaleGestureDetector {
        return ScaleGestureDetector(context, pinchGestureListener)
    }
}