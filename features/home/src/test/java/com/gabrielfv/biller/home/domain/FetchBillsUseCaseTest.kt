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

import com.gabrielfv.biller.home.data.FakeSource
import com.gabrielfv.biller.home.domain.entities.PaymentState
import com.gabrielfv.biller.home.domain.mappers.PaymentStateMapper
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.Month

typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class FetchBillsUseCaseTest {
    private val source = FakeSource()
    private val stateMapper = PaymentStateMapper(mockk {
        every { getMonthAndDay(any()) } returns Pair(Month.FEBRUARY, 2)
        every { getYear(any()) } returns 2020
    })

    @Test
    fun noDataReturnsEmpty() {
        source.response = FakeSource.Response.EMPTY
        val subject = FetchBillsUseCase(source, stateMapper)
        val expected = listOf<DomainBill>()

        val result = runBlocking { subject.execute() }

        assertThat(result, `is`(expected))
    }

    @Test
    fun correctlyChoosesLatestPayment() {
        source.response = FakeSource.Response.UNORDERED
        val subject = FetchBillsUseCase(source, stateMapper)

        val result = runBlocking { subject.execute() }

        val first = result.first()
        assertThat(first.paymentState, `is`(PaymentState.PAID))
        assertThat(first.valueInCents, `is`(1500))
    }

    @Test
    fun correctlyMapsPaymentStates() {
        source.response = FakeSource.Response.STATES
        val subject = FetchBillsUseCase(source, stateMapper)

        val result = runBlocking { subject.execute() }

        val statesByName = result
                .map { Pair(it.name, it.paymentState) }
                .toMap()
        assertThat(statesByName["A"], `is`(PaymentState.EXPIRED))
        assertThat(statesByName["B"], `is`(PaymentState.TO_BE_EXPIRED))
        assertThat(statesByName["C"], `is`(PaymentState.OPEN))
        assertThat(statesByName["D"], `is`(PaymentState.PAID))
        assertThat(statesByName["E"], `is`(PaymentState.EXPIRED))
    }
}
