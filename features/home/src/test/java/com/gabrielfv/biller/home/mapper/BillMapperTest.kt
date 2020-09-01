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

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class BillMapperTest {

    @Test
    fun correctlyMapsIdAndName() {
        val subject = BillMapper()
        val input = DomainBill(2, "The Name", 0)

        val result = subject.map(input)

        assertThat(result.id, `is`(2))
        assertThat(result.name, `is`("The Name"))
    }

    @Test
    fun correctlyMapsCents() {
        val subject = BillMapper()
        val inputs = listOf(
            DomainBill(0, "", 1),
            DomainBill(0, "", 12),
            DomainBill(0, "", 123),
        )

        val results = inputs.map(subject::map)

        assertThat(results[0].valueCents, `is`(1))
        assertThat(results[1].valueCents, `is`(12))
        assertThat(results[2].valueCents, `is`(23))
    }

    @Test
    fun correctlyAppliesDividersOnLargeNumbers() {
        val subject = BillMapper()
        val inputs = listOf(
            DomainBill(0, "", 123400),
            DomainBill(0, "", 1234500),
            DomainBill(0, "", 12345600),
            DomainBill(0, "", 123456700),
        )

        val results = inputs.map(subject::map)

        assertThat(results[0].valueWhole, `is`("1,234"))
        assertThat(results[1].valueWhole, `is`("12,345"))
        assertThat(results[2].valueWhole, `is`("123,456"))
        assertThat(results[3].valueWhole, `is`("1,234,567"))
    }

    @Test
    fun correctlyAppliesSpecifiedDivider() {
        val subject = BillMapper()
        val input = DomainBill(0, "", 1234500)

        val result = subject.map(input, '-')

        assertThat(result.valueWhole, `is`("12-345"))
    }
}