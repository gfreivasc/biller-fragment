package com.gabrielfv.core.arch.tests

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

class CounterSetup {

    fun start() {
        launchFragmentInContainer<CounterController>()
    }
}

class CounterActions {

    fun inc() {
        onView(withId(R.id.incButton)).perform(click())
    }

    fun dec() {
        onView(withId(R.id.decButton)).perform(click())
    }
}

class CounterAssertions {

    fun currentCountIs(count: Int) {
        onView(withId(R.id.counter)).check(matches(withText("$count")))
    }
}