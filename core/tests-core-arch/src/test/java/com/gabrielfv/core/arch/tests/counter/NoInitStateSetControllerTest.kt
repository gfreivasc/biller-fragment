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
import com.gabrielfv.core.arch.tests.NoInitStateSetController
import com.gabrielfv.core.test.start
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class NoInitStateSetControllerTest  {
    private val view = mockk<View<CounterState>>(relaxed = true)

    @Test
    fun breaksOnInitWithIllegalState() {
        val subject = NoInitStateSetController { view }

        val result = try {
            subject.start()
        } catch (ex: IllegalStateException) {
            ex
        }

        result as IllegalStateException
        assertThat(
            result.message,
            `is`(
                """
                    [NoInitStateSetController.onInitialize()] does not
                    guarantee state integrity. Make sure to either set state
                    via [setState()] or by calling [super.onInitialize()] with
                    a set [initialState]
                """.trimIndent()
            )
        )
    }
}
