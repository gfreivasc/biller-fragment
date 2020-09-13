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
package com.gabrielfv.core.ktx

import android.os.Parcel
import io.mockk.*
import kotlinx.datetime.toLocalDate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.Month

class LocalDateParcelerTest {
    private val mockParcel = mockk<Parcel>()
    private val dummyDateString = "1999-12-31"

    @Test
    fun correctlyWritesToParcel() {
        val localDate = dummyDateString.toLocalDate()
        every { mockParcel.writeString(any()) } just Runs

        with(LocalDateParceler) {
            localDate.write(mockParcel, 0)
        }

        verify { mockParcel.writeString(eq(dummyDateString)) }
    }

    @Test
    fun correctlyReadsFromParcel() {
        every { mockParcel.readString() } returns dummyDateString

        val result = LocalDateParceler.create(mockParcel)

        assertThat(result.dayOfMonth, `is`(31))
        assertThat(result.month, `is`(Month.DECEMBER))
        assertThat(result.year, `is`(1999))
    }
}
