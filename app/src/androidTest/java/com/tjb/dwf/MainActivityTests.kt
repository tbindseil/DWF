package com.tjb.dwf

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTests {
    @Test fun testEvent() {
        val scenario = launchActivity<MainActivity>()
    }
}

