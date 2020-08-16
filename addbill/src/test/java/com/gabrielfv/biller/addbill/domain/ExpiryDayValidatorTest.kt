package com.gabrielfv.biller.addbill.domain

import com.gabrielfv.biller.addbill.domain.errors.ExpiryDayError
import com.gabrielfv.core.test.isA
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExpiryDayValidatorTest {

    @Test
    fun regularDaysAreValid() {
        val days = 1..28
        val subject = ExpiryDayValidator()

        val results = days.map { subject.execute(it) }

        results.forEach { assertThat(it, isA<Either.Right<*>>()) }
    }

    @Test
    fun daysBelowOneAreNotRegularMonthDays() {
        val days = listOf(0, -1)
        val subject = ExpiryDayValidator()

        val results = days.map { subject.execute(it) }

        results.forEach { assertThat(it, isA<Either.Left<*>>()) }
        results.forEach { assertThat(it.toLeft(), `is`(ExpiryDayError.NotARecurringMonthDay)) }
    }

    @Test
    fun recurringDaysNotRecurringInFebruaryAreUnsafe() {
        val days = listOf(29, 30)
        val subject = ExpiryDayValidator()

        val results = days.map { subject.execute(it) }

        results.forEach { assertThat(it, isA<Either.Left<*>>()) }
        results.forEach { assertThat(it.toLeft(), `is`(ExpiryDayError.NotSafeForFebruary)) }
    }
}