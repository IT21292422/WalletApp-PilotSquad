package com.example.madproject

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginTesting {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.madproject", appContext.packageName)
    }
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(Login::class.java)
    private lateinit var activityScenario: ActivityScenario<Login>

    @Before
    fun setUp() {
        activityScenario = activityScenarioRule.scenario
        activityScenario.onActivity { activity ->

        }
    }

    @Test
    fun testNullUserNamePassword() {
        onView(withId(R.id.edtUserName)).perform(typeText(""))
        onView(withId(R.id.edtPassword)).perform(typeText(""))
        onView(withId(R.id.btnLogin)).perform(click())
        onView(withId(R.id.edtUserName)).check(ViewAssertions.matches(withHint("User Name can not be empty")))
        onView(withId(R.id.edtPassword)).check(ViewAssertions.matches(withHint("password can not be empty")))
    }
    @After
    fun tearDown() {
        activityScenario.close()
    }
}