package com.tjb.dwf

import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import org.junit.Rule
import androidx.test.ext.junit.rules.activityScenarioRule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTests {
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun passes() {

    }
}
