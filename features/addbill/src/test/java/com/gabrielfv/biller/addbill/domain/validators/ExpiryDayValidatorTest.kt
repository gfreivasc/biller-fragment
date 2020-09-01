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
package com.gabrielfv.biller.addbill.domain.validators

import com.gabrielfv.biller.addbill.domain.Either
import com.gabrielfv.biller.addbill.domain.entities.errors.ExpiryDayError
import com.gabrielfv.biller.addbill.domain.toLeft
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