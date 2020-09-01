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

import android.os.Parcelable
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.android.parcel.Parcelize
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class BindingViewTest {
    private val inflater = mockk<LayoutInflater>(relaxed = true)

    @Test
    fun inflatingRequestsBinding() {
        val subject = spyk(instantiate())

        subject.inflate(inflater, mockk())

        verify { subject.bind(any()) }
    }

    @Test
    fun bindingHoldsPassedValue() {
        val subject = instantiate()

        subject.inflate(inflater, mockk())

        assertThat(subject.binding, `is`(VoidBindingView.binding))
    }

    @Test
    fun bindingChainsStartSequence() {
        val subject = spyk(instantiate())

        subject.inflate(inflater, mockk())

        verify { subject.onStart() }
    }

    @Test
    fun updatingStateCallsOnNewState() {
        val subject = spyk(instantiate())
        subject.inflate(inflater, mockk())

        subject.updateState(TestState(1))

        verify { subject.onNewState(eq(TestState(1))) }
    }

    @Test
    fun updatingStateWithoutInitializedBindingMakesNothing() {
        val subject = spyk(instantiate())

        subject.updateState(TestState(1))

        verify(exactly = 0) { subject.onNewState(eq(TestState(1))) }
    }

    @Test
    fun updatingStateAfterDestroyingMakesNothing() {
        val subject = spyk(instantiate())
        subject.inflate(inflater, mockk())
        subject.destroy()

        subject.updateState(TestState(1))

        verify(exactly = 0) { subject.onNewState(eq(TestState(1))) }
    }

    @Test(expected = NullPointerException::class)
    fun destroyingClearsBinding() {
        val subject = instantiate()
        subject.inflate(inflater, mockk())

        subject.destroy()

        subject.binding
    }

    @Parcelize
    data class TestState(val count: Int) : Parcelable

    class TestController(
        provider: ViewProvider<TestController, TestState>
    ) : Controller<TestState>() {
        override val view: View<TestState> = provider.get(this)
        override val initialState = TestState(0)
    }

    class VoidBindingView(
        override val controller: TestController
    ) : BindingView<ViewBinding, TestState>(controller, 0) {

        override fun bind(view: android.view.View): ViewBinding {
            return Companion.binding
        }

        override fun onStart() = Unit

        override fun onNewState(state: TestState) = Unit

        companion object : ViewProvider<TestController, TestState> {
            val binding = ViewBinding { mockk() }
            override fun get(controller: TestController): View<TestState> {
                return VoidBindingView(controller)
            }
        }
    }

    private fun instantiate(): VoidBindingView {
        val controller = TestController(VoidBindingView)
        return controller.view as VoidBindingView
    }
}
