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
package com.gabrielfv.core.test

import com.gabrielfv.core.test.dummies.AController
import com.gabrielfv.core.test.resources.spyingRes
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DslIntegrationTest {
    private val controller = AController()

    @Test
    fun testDefaultModeForGetString() {
        val subject = spyingRes(controller)

        val result = subject.getString(0)

        assertThat(result, `is`(""))
    }

    @Test
    fun testDefaultModeForGetText() {
        val subject = spyingRes(controller)

        val result = subject.getText(0)

        assertThat(result, `is`(""))
    }

    @Test
    fun testSettingStringReturnsCorrectly() {
        val subject = spyingRes(controller) { 0 toString  "Not empty" }

        val result = subject.getString(0)

        assertThat(result, `is`("Not empty"))
    }
}