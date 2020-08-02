package com.tjb.dwf

import android.app.Application
import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerAppComponent

open class DWFApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}
