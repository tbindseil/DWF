package com.tjb.dwf

import android.app.Application
import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerAppComponent

// I think I need a main activity component, then my life will be complete
open class DWFApplication : Application() {
    // todo see below
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    //private var appComponent = initializeComponent()

    /*public fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = initializeComponent()
        }
        return appComponent
    }*/

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    // TODO maybe test application class extending this one?
    // this is what moves to the other one
    //public fun setTestAppComponent(appComponent: AppComponent) {
        //this.appComponent = appComponent
    //}
}
