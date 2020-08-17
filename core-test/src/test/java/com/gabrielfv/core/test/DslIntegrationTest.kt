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