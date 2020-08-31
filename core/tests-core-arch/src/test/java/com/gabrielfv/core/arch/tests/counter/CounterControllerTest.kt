package com.gabrielfv.core.arch.tests.counter

import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.tests.CounterController
import com.gabrielfv.core.arch.tests.CounterState
import com.gabrielfv.core.test.start
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CounterControllerTest {
    private val view = mockk<View<CounterState>>(relaxed = true)

    @Test
    fun stateInitializesWithCount0() {
        val subject = CounterController { view }

        subject.start()

        verify { view.updateState(eq(CounterState(0))) }
    }

    @Test
    fun incIncrementsCounter() {
        val subject = CounterController { view }
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
        val subject = CounterController { view }
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
}
