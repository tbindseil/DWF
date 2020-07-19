package com.tjb.dwf.main

import android.util.Log
import com.tjb.dwf.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class PinchGestureReceiver @Inject constructor() {
    private var mainActivity: MainActivity? = null

    fun installMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    fun uninstallMainActivity() {
        this.mainActivity = null
    }

    fun showOptions() {
        // note, all this nullable crap should be not an issue once this is scoped to main activities lifecycle
        if (mainActivity != null) {
            mainActivity?.showOptions()
        } else {
            Log.e("PinchGestureReceiver", "MainActivity is null")
        }
    }

    fun showPicture() {
        // note, all this nullable crap should be not an issue once this is scoped to main activities lifecycle
        if (mainActivity != null) {
            mainActivity?.showPicture()
        } else {
            Log.e("PinchGestureReceiver", "MainActivity is null")
        }
    }
}
