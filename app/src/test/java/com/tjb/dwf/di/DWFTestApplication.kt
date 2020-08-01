package com.tjb.dwf.di

import com.tjb.dwf.DWFApplication

class DWFTestApplication : DWFApplication() {
    override fun initializeComponent(): AppComponent {
        // .... seriously, what did I do?
        // 7/31 I:
        // 1 added kaptTest to gradle
        // 2 made test component and added extension to dwfapplication
        // 3 got stuck making TestDaggerAppComponent work below. Why won't DaggerAppComponent generate?
        //
        // 8/1
        // 1) IUserController, implemented by UserController, then in test implemented by MockUserController with get mock - not done
        // 2) extend test runner to use DWFTestApplication - not started
        //return DaggerTestAppComponent.factory().create(applicationContext)
        return DaggerAppComponent.factory().create(applicationContext)
    }
}