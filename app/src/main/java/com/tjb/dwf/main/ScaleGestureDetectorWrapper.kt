package com.tjb.dwf.main

import android.content.Context
import android.view.ScaleGestureDetector
import com.tjb.dwf.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class ScaleGestureDetectorWrapper @Inject constructor(context: Context, pinchGestureListener: PinchGestureListener) :
        ScaleGestureDetector(context, pinchGestureListener) {
}