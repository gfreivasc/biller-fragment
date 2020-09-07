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
package com.gabrielfv.biller.home.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DateTimeSourceTest {
    private val clock = object :  Clock {
        override fun now(): Instant {
            // Jan 1 1970 - 00:00 in UTC
            return Instant.fromEpochSeconds(0)
        }
    }

    @Test
    fun getMonthAndDayReturnsCorrectMonthAndDay() {
        val subject = DateTimeSource(clock)

        val (month, day) = subject.getMonthAndDay(TimeZone.UTC)

        assertThat(month, `is`(Month.JANUARY))
        assertThat(day, `is`(1))
    }

    @Test
    fun getYearReturnsCorrectYear() {
        val subject = DateTimeSource(clock)

        val year = subject.getYear(TimeZone.UTC)

        assertThat(year, `is`(1970))
    }
}
