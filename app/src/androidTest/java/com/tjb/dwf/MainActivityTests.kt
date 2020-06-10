package com.tjb.dwf

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTests {
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun onResume_startsLoginActivity_whenUserDataIsNotFound() {
        activityScenarioRule.launch()
        var scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        intended(hasComponent(LoginActivity::class.java.getName()))
    }
}
