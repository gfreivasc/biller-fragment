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
package com.gabrielfv.core.arch.tests.counter

import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.tests.CounterState
import com.gabrielfv.core.arch.tests.InitStateSetController
import com.gabrielfv.core.test.start
import com.gabrielfv.core.test.stateBundle
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class InitStateSetControllerTest {
    private val view = mockk<View<CounterState>>(relaxed = true)

    @Test
    fun stateInitializesWithCount0() {
        val subject = InitStateSetController { view }

        subject.start()

        verify { view.updateState(eq(CounterState(0))) }
    }

    @Test
    fun incIncrementsCounter() {
        val subject = InitStateSetController { view }
        subject.start()

        subject.inc()
        subject.inc()

        verify {
            view.updateState(eq(CounterState(0)))  // init
            view.updateState(eq(CounterState(1)))
            view.updateState(eq(CounterState(2)))
        }
    }

    @Test
    fun decDecrementsCounter() {
        val subject = InitStateSetController { view }
        subject.start()
        subject.inc()
        subject.inc()

        subject.dec()
        subject.dec()

        verify {
            view.updateState(eq(CounterState(0)))  // init
            view.updateState(eq(CounterState(1)))  // inc
            view.updateState(eq(CounterState(2)))  // inc
            view.updateState(eq(CounterState(1)))
            view.updateState(eq(CounterState(0)))
        }
    }

    @Test
    fun stateIsRestoredWhenSaved() {
        val subject = InitStateSetController { view }

        subject.start(stateBundle(CounterState(4)))

        verify { view.updateState(eq(CounterState(4))) }
    }
}
