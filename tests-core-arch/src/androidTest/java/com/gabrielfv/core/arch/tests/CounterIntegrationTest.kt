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