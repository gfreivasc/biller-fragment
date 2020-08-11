package com.gabrielfv.core.arch.tests.counter

import android.os.Bundle
import android.os.Parcelable
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.tests.CounterController
import com.gabrielfv.core.arch.tests.CounterState
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CounterControllerTest {
    private val view = mockk<View<CounterState>>(relaxed = true)

    @Test
    fun stateInitializesWithCount0() {
        // Given
        val subject = CounterController { view }

        // When
        subject.start()

        // Then
        verify { view.updateState(eq(CounterState(0))) }
    }

    @Test
    fun incIncrementsCounter() {
        // Given
        val subject = CounterController { view }
        subject.start()

        // When
        subject.inc()
        subject.inc()

        // Then
        verify {
            view.updateState(eq(CounterState(0)))  // init
            view.updateState(eq(CounterState(1)))
            view.updateState(eq(CounterState(2)))
        }
    }

    @Test
    fun decDecrementsCounter() {
        // Given
        val subject = CounterController { view }
        subject.start()
        subject.inc()
        subject.inc()

        // When
        subject.dec()
        subject.dec()

        // Then
        verify {
            view.updateState(eq(CounterState(0)))  // init
            view.updateState(eq(CounterState(1)))  // inc
            view.updateState(eq(CounterState(2)))  // inc
            view.updateState(eq(CounterState(1)))
            view.updateState(eq(CounterState(0)))
        }
    }

    private fun <T : Parcelable> Controller<T>.start(savedState: Bundle? = null) {
        onActivityCreated(savedState)
    }
}