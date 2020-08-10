package com.tjb.dwf.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewActivityIntentFactory @Inject constructor() {
    fun makeNewActivityIntent(packageContext: Context, cls: Class<out Activity>): Intent {
        return Intent(packageContext, cls)
    }
}