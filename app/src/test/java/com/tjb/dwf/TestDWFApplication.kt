package com.tjb.dwf

import com.tjb.dwf.di.AppComponent
import com.tjb.dwf.di.DaggerMockAppComponent

class TestDWFApplication : DWFApplication() {
    override fun initializeComponent(): AppComponent {
        return DaggerMockAppComponent.factory().create(applicationContext)
    }
}