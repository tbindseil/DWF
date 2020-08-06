package com.tjb.dwf.main

import android.util.Log
import android.view.ScaleGestureDetector
import com.tjb.dwf.di.ActivityScope

import javax.inject.Inject

@ActivityScope
class PinchGestureListener @Inject constructor() :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {

    private var mainActivity: MainActivity? = null

    companion object {
        private const val THRESHOLD: Int = 9
    }

    private var positiveCount: Int = 0
    private var negativeCount: Int = 0

    fun installMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

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
            showPicture()
        } else if (negativeCount > THRESHOLD) {
            showOptions()
        }

        return true
    }

    private fun showOptions() {
        // note, all this nullable crap should be not an issue once this is scoped to main activities lifecycle
        if (mainActivity != null) {
            mainActivity?.showOptions()
        } else {
            Log.e("PinchGestureReceiver", "MainActivity is null")
        }
    }

    private fun showPicture() {
        // note, all this nullable crap should be not an issue once this is scoped to main activities lifecycle
        if (mainActivity != null) {
            mainActivity?.showPicture()
        } else {
            Log.e("PinchGestureReceiver", "MainActivity is null")
        }
    }
}
