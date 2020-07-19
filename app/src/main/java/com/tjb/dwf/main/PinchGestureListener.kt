package com.tjb.dwf.main

import android.view.ScaleGestureDetector

import javax.inject.Inject

class PinchGestureListener @Inject constructor(
            private val pinchGestureReceiver: PinchGestureReceiver) :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {

    companion object {
        private const val THRESHOLD: Int = 9
    }

    private var positiveCount: Int = 0
    private var negativeCount: Int = 0

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        positiveCount = 0
        negativeCount = 0
        return true
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val scaleFactor: Float = detector.scaleFactor

        if (scaleFactor > 1.0) {
            ++positiveCount
            negativeCount = 0
        } else if (scaleFactor < 1.0) {
            ++negativeCount
            positiveCount = 0
        }

        if (positiveCount > THRESHOLD) {
            pinchGestureReceiver.showPicture()
        } else if (negativeCount > THRESHOLD) {
            pinchGestureReceiver.showOptions()
        }

        return true
    }
}
