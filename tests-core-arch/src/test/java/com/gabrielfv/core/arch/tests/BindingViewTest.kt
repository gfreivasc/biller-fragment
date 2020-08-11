package com.gabrielfv.core.arch.tests

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
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
import org.junit.Assert.assertThat
import org.junit.Test

class BindingViewTest {

    @Test
    fun inflatingRequestsBinding() {
        // Given
        val subject = spyk(instantiate())

        // When
        subject.inflate(mockk(), mockk())

        // Then
        verify { subject.bind(any(), any()) }
    }

    @Test
    fun bindingHoldsPassedValue() {
        // Given
        val subject = instantiate()

        // When
        subject.inflate(mockk(), mockk())

        // Then
        assertThat(subject.binding, `is`(VoidBindingView.binding))
    }

    @Test
    fun bindingChainsStartSequence() {
        // Given
        val subject = spyk(instantiate())

        // When
        subject.inflate(mockk(), mockk())

        // Then
        verify { subject.onStart() }
    }

    @Test
    fun updatingStateCallsOnNewState() {
        // Given
        val subject = spyk(instantiate())
        subject.inflate(mockk(), mockk())

        // When
        subject.updateState(TestState(1))

        // Then
        verify { subject.onNewState(eq(TestState(1))) }
    }

    @Test
    fun updatingStateWithoutInitializedBindingMakesNothing() {
        // Given
        val subject = spyk(instantiate())

        // When
        subject.updateState(TestState(1))

        // Then
        verify(exactly = 0) { subject.onNewState(eq(TestState(1))) }
    }

    @Test
    fun updatingStateAfterDestroyingMakesNothing() {
        // Given
        val subject = spyk(instantiate())
        subject.inflate(mockk(), mockk())
        subject.destroy()

        // When
        subject.updateState(TestState(1))

        // Then
        verify(exactly = 0) { subject.onNewState(eq(TestState(1))) }
    }

    @Test(expected = NullPointerException::class)
    fun destroyingClearsBinding() {
        // Given
        val subject = instantiate()
        subject.inflate(mockk(), mockk())

        // When
        subject.destroy()

        // Then
        subject.binding
    }

    @Parcelize
    data class TestState(val count: Int) : Parcelable

    class TestController(
        provider: ViewProvider<TestController, TestState>
    ) : Controller<TestState>() {
        override val view: View<TestState> = provider.get(this)

        override fun initialize() = TestState(0)
    }

    class VoidBindingView(
        override val controller: TestController
    ) : BindingView<ViewBinding, TestState>(controller) {

        override fun bind(
            inflater: LayoutInflater,
            container: ViewGroup?
        ): ViewBinding {
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