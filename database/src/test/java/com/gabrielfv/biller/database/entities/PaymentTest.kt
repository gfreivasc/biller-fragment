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
package com.gabrielfv.biller.database.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.junit.Test

class PaymentTest {

    @Test
    fun correctlyComparesPaymentsByDate() {
        val a = payment("2000-01-02".toLocalDate())
        val b = payment("2000-01-03".toLocalDate())
        val c = payment("2004-12-31".toLocalDate())
        val d = payment("2005-01-01".toLocalDate())

        assert(b > a)
        assert(c > b)
        assert(d > c)
        assert(a < d)
    }

    private fun payment(date: LocalDate) = Payment(
        date = date,
        billId = 0L,
        valueInCents = 1000,
    )
}
