package com.tjb.dwf

import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerTestAppComponent

class TestDWFApplication : DWFApplication() {
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.factory().create(applicationContext)
    }
}