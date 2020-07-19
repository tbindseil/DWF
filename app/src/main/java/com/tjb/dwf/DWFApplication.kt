package com.tjb.dwf

import android.app.Application
import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerAppComponent

// I think I need a main activity component, then my life will be complete
class DWFApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
