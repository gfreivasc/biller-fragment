package com.gabrielfv.core.arch.tests

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.Destroyable
import com.gabrielfv.core.arch.View
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.parcel.Parcelize
import org.junit.Test

private const val STATE_REG = "com.gabrielfv.core.arch.STATE_REGISTRY"

class ControllerTest {

    @Test
    fun onCreateViewInflatesViewInstance() {
        // Given
        val inflater = mockk<LayoutInflater>()
        val container = mockk<ViewGroup>()
        val controller = TestController()

        // When
        controller.onCreateView(inflater, container, null)

        // Then
        verify { controller.view.inflate(refEq(inflater), refEq(container)) }
    }

    @Test
    fun onActivityCreatedWithSavedStateExposeSavedState() {
        // Given
        val controller = TestController()

        // When
        controller.onActivityCreated(bundleWithData)

        // Then
        verify { controller.view.updateState(eq(TestState((5)))) }
    }

    @Test
    fun onActivityCreatedWithoutStateExposeInitialState() {
        // Given
        val controller = TestController()

        // When
        controller.onActivityCreated(null)

        // Then
        verify { controller.view.updateState(eq(TestState((0)))) }
    }

    @Test
    fun onSaveInstanceWithStateShouldSaveIt() {
        // Given
        val controller = TestController()
        val bundle = mockk<Bundle>(relaxed = true)
        controller.init()

        // When
        controller.onSaveInstanceState(bundle)

        // Then
        verify { bundle.putParcelable(eq(STATE_REG), eq(TestState(0))) }
    }

    @Test
    fun onSaveInstanceWithoutStateShouldDoNothing() {
        // Given
        val controller = TestController()
        val bundle = mockk<Bundle>(relaxed = true)

        // When
        controller.onSaveInstanceState(bundle)

        // Then
        verify(exactly = 0) { bundle.putParcelable(any(), any()) }
    }

    @Test
    fun registeredDestroyablesAreDestroyedWithController() {
        // Given
        val controller = TestController()
        val destroyable = mockk<Destroyable>(relaxed = true)

        // When
        controller.registerDestroyable(destroyable)
        controller.onDestroy()

        // Then
        verify { destroyable.destroy() }
    }

    class TestController : Controller<TestState>() {
        override val view: View<TestState> = mockk(relaxed = true)

        override fun initialize() = TestState(0)

        fun init() = setState { initialize() }
    }

    @Parcelize
    data class TestState(val count: Int) : Parcelable

    private val bundleWithData = mockk<Bundle> {
        every {
            getParcelable<TestState>(eq(STATE_REG))
        } returns TestState(5)
    }
}