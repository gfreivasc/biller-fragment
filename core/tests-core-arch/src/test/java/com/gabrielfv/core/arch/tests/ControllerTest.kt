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

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavHostController
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.Destroyable
import com.gabrielfv.core.arch.View
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.android.parcel.Parcelize
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.lang.IllegalStateException

private const val STATE_REG = "com.gabrielfv.core.arch.STATE_REGISTRY"

class ControllerTest {

    @Test
    fun onCreateViewInflatesViewInstance() {
        val inflater = mockk<LayoutInflater>()
        val container = mockk<ViewGroup>()
        val controller = TestController()

        controller.onCreateView(inflater, container, null)

        verify { controller.view.inflate(refEq(inflater), refEq(container)) }
    }

    @Test
    fun onActivityCreatedWithSavedStateExposeSavedState() {
        val controller = TestController()

        controller.onActivityCreated(bundleWithData)

        verify { controller.view.updateState(eq(TestState((5)))) }
    }

    @Test
    fun onActivityCreatedWithoutStateExposeInitialState() {
        val controller = TestController()

        controller.onActivityCreated(null)

        verify { controller.view.updateState(eq(TestState((0)))) }
    }

    @Test
    fun onSaveInstanceWithStateShouldSaveIt() {
        val controller = TestController()
        val bundle = mockk<Bundle>(relaxed = true)
        controller.onActivityCreated(null)

        controller.onSaveInstanceState(bundle)

        verify { bundle.putParcelable(eq(STATE_REG), eq(TestState(0))) }
    }

    @Test
    fun registeredDestroyableSetIsDestroyedWithController() {
        val controller = TestController()
        val destroyable = mockk<Destroyable>(relaxed = true)

        controller.registerDestroyable(destroyable)
        controller.onDestroy()

        verify { destroyable.destroy() }
    }

    @Test
    fun requestNavControllerReturnsSetController() {
        val navController = mockk<NavHostController>()
        val controller = TestController()
        controller.navController = navController

        val result = controller.findNavController()

        assertThat(result, `is`(navController))
    }

    @Test
    fun requestNavControllerWithoutSetControllerShouldDefault() {
        val controller = TestController()

        val result = try {
            controller.findNavController()
        } catch (ex: IllegalStateException) {
            ex
        }

        assertThat(result, instanceOf(IllegalStateException::class.java))
    }

    class TestController : Controller<TestState>() {
        override val view: View<TestState> = mockk(relaxed = true)
        override val initialState = TestState(0)
    }

    @Parcelize
    data class TestState(val count: Int) : Parcelable

    private val bundleWithData = mockk<Bundle> {
        every {
            getParcelable<TestState>(eq(STATE_REG))
        } returns TestState(5)
    }
}
