package com.gabrielfv.core.arch

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
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
        verify { subject.bind(any(), any(), any()) }
    }

    @Test
    fun bindingHoldsPassedValue() {
        // Given
        val subject = instantiate()

        // When
        subject.inflate(mockk(), mockk())

        // Then
        assertThat(subject.binding, `is`(IntBindingView.BIND))
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

    class TestController(provider: ViewProvider<TestController, TestState>) : Controller<TestState>() {
        override val view: View<TestState> = provider.get(this)

        override fun initialize() = TestState(0)
    }

    class IntBindingView(
        override val controller: TestController
    ) : BindingView<Int, TestState>(controller) {

        override fun bind(
            inflater: LayoutInflater,
            container: ViewGroup?,
            binder: (Int) -> Unit
        ): android.view.View {
            binder(BIND)
            return mockk()
        }

        override fun onNewState(state: TestState) = Unit

        companion object : ViewProvider<TestController, TestState> {
            const val BIND = 4
            override fun get(controller: TestController): View<TestState> {
                return IntBindingView(controller)
            }
        }
    }

    private fun instantiate(): IntBindingView {
        val controller = TestController(IntBindingView)
        return controller.view as IntBindingView
    }
}