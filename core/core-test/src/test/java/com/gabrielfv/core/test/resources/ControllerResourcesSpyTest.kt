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
