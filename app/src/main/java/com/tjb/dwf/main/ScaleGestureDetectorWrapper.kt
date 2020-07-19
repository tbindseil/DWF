package com.tjb.dwf.main

import android.content.Context
import android.view.ScaleGestureDetector
import javax.inject.Inject

class ScaleGestureDetectorWrapper @Inject constructor(context: Context, pinchGestureListener: PinchGestureListener) :
        ScaleGestureDetector(context, pinchGestureListener) {
}