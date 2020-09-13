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
package com.gabrielfv.biller.database.converters

import kotlinx.datetime.Month
import kotlinx.datetime.toLocalDate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class LocalDateConverterTest {
    private val dummyDateString = "1994-06-03"

    @Test
    fun correctlyConvertsToString() {
        val subject = LocalDateConverter()
        val localDate = dummyDateString.toLocalDate()

        val result = subject.toString(localDate)

        assertThat(result, `is`(dummyDateString))
    }

    @Test
    fun correctlyConvertsFromString() {
        val subject = LocalDateConverter()

        val result = subject.fromString(dummyDateString)

        assertThat(result?.dayOfMonth, `is`(3))
        assertThat(result?.month, `is`(Month.JUNE))
        assertThat(result?.year, `is`(1994))
    }
}
