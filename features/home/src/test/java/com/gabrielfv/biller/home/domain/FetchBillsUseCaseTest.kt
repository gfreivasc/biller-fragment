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
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class FetchBillsUseCaseTest {
    private val source = FakeSource()
    private val timeZone = TimeZone.UTC
    private val stateMapper = PaymentStateMapper(mockk {
        every { now() } returns Instant.parse(
            "2020-02-02T00:00:00Z"
        )
    }, timeZone)

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
        assertThat(first.paymentState.state, `is`(PaymentState.State.PAID))
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
        assertThat(statesByName["A"]?.state, `is`(PaymentState.State.EXPIRED))
        assertThat(statesByName["B"]?.state, `is`(PaymentState.State.TO_BE_EXPIRED))
        assertThat(statesByName["C"]?.state, `is`(PaymentState.State.OPEN))
        assertThat(statesByName["D"]?.state, `is`(PaymentState.State.PAID))
        assertThat(statesByName["E"]?.state, `is`(PaymentState.State.EXPIRED))
        assertThat(statesByName["F"]?.state, `is`(PaymentState.State.FORGOTTEN))
    }
}
