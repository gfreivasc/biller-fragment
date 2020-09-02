/*
 * Copyright 2020 Gabriel Freitas Vasconcelos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabrielfv.core.arch.tests

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

class CounterSetup(private val factory: FragmentFactory? = null) {

    fun start() {
        launchFragmentInContainer<DefaultCounterController>(factory = factory)
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
