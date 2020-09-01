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

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterIntegrationTest {

    @Test
    fun testInitializeState() {
        setup.start()

        assert.currentCountIs(0)
    }

    @Test
    fun testIncrementActionIncrementsCounter() {
        setup.start()

        actor.inc()
        assert.currentCountIs(1)

        actor.inc()
        assert.currentCountIs(2)
    }

    @Test
    fun testDecrementActionDecrementsCounter() {
        setup.start()

        actor.inc()
        actor.inc()
        actor.inc()
        actor.dec()
        assert.currentCountIs(2)

        actor.dec()
        assert.currentCountIs(1)
    }

    private val setup = CounterSetup()
    private val actor = CounterActions()
    private val assert = CounterAssertions()
}