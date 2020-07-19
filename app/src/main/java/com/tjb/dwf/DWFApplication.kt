package com.tjb.dwf

import android.app.Application
import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerAppComponent

class DWFApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}
