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
package com.gabrielfv.biller.home.mapper

import com.gabrielfv.biller.home.domain.entities.PaymentState
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class BillMapperTest {

    @Test
    fun correctlyMapsIdAndName() {
        val subject = BillMapper()
        val input = domain(2L, "The Name")

        val result = subject.map(input)

        assertThat(result.id, `is`(2L))
        assertThat(result.name, `is`("The Name"))
    }

    @Test
    fun correctlyMapsCents() {
        val subject = BillMapper()
        val inputs = listOf(
            domain(valueInCents = 1),
            domain(valueInCents = 12),
            domain(valueInCents = 123),
        )

        val results = inputs.map { subject.map(it) }

        assertThat(results[0].payment?.valueCents, `is`(1))
        assertThat(results[1].payment?.valueCents, `is`(12))
        assertThat(results[2].payment?.valueCents, `is`(23))
    }

    @Test
    fun correctlyAppliesDividersOnLargeNumbers() {
        val subject = BillMapper()
        val inputs = listOf(
            domain(valueInCents = 123400),
            domain(valueInCents = 1234500),
            domain(valueInCents = 12345600),
            domain(valueInCents = 123456700),
        )

        val results = inputs.map { subject.map(it) }

        assertThat(results[0].payment?.valueWhole, `is`("1,234"))
        assertThat(results[1].payment?.valueWhole, `is`("12,345"))
        assertThat(results[2].payment?.valueWhole, `is`("123,456"))
        assertThat(results[3].payment?.valueWhole, `is`("1,234,567"))
    }

    @Test
    fun correctlyAppliesSpecifiedDivider() {
        val subject = BillMapper()
        val input = domain(valueInCents = 1234500)

        val result = subject.map(input, '-')

        assertThat(result.payment?.valueWhole, `is`("12-345"))
    }

    private fun domain(
        id: Long = 0L,
        name: String = "",
        paymentState: PaymentState = PaymentState.OPEN,
        fixedValue: Boolean = true,
        valueInCents: Int = 0,
    ) = DomainBill(id, name, paymentState, fixedValue, valueInCents)
}
