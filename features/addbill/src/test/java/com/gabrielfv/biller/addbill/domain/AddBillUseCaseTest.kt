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
package com.gabrielfv.biller.addbill.domain

import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.interfaces.BillsSource
import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.core.arch.coroutines.InstantCoroutinesExecutor
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Test

class AddBillUseCaseTest {
    private val source = mockk<BillsSource>(relaxed = true)
    private val executor = InstantCoroutinesExecutor()
    private val localDate = Instant.fromEpochSeconds(0)
            .toLocalDateTime(TimeZone.UTC)
            .date
    private val clock = mockk<Clock> {
        // UTC 1 Jan 1970 00:00
        every { now() } returns Instant.fromEpochSeconds(0)
    }

    @Test
    fun savesValueWithFixedValue() {
        val subject = AddBillUseCase(source, clock, TimeZone.UTC)
        val expected = Bill(
            registeredAt = localDate,
            name = "A",
            expiryDay = 5,
            fixedValue = true,
            valueInCents = 100,
        )

        executor.execute {
            subject.execute(NewBill(
                "A",
                5,
                true,
                100,
            ))
        }

        coVerify { source.insert(eq(expected)) }
    }

    @Test
    fun ignoresValueWithoutFixedValue() {
        val subject = AddBillUseCase(source, clock, TimeZone.UTC)
        val expected = Bill(
            registeredAt = localDate,
            name = "A",
            expiryDay = 5,
            fixedValue = false,
            valueInCents = null,
        )

        executor.execute {
            subject.execute(NewBill(
                "A",
                5,
                false,
                100,
            ))
        }

        coVerify { source.insert(eq(expected)) }
    }
}
