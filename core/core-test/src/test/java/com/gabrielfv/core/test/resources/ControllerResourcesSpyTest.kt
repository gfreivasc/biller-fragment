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
package com.gabrielfv.core.test.resources

import com.gabrielfv.core.test.dummies.AController
import io.mockk.spyk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ControllerResourcesSpyTest {
    private val spiedController = spyk(AController())

    @Test
    fun defaultsAnyStringToEmpty() {
        initialize()

        val result = spiedController.getString(0)
        assertThat(result, `is`(""))
    }

    @Test
    fun defaultsAnyTextToEmpty() {
        initialize()

        val result = spiedController.getText(0)
        assertThat(result, `is`(""))
    }

    @Test
    fun replacesSpecifiedIdWithString() {
        val subject = initialize()

        with(subject) { 0 toString "A string" }

        val result = spiedController.getString(0)
        assertThat(result, `is`("A string"))
    }

    private fun initialize(): ControllerResourcesSpy {
        return ControllerResourcesSpy(spiedController)
    }
}
