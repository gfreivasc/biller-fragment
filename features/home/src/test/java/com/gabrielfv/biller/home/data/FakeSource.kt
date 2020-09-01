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
package com.gabrielfv.biller.home.data

import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.biller.database.entities.BillHistory
import com.gabrielfv.biller.database.entities.Payment
import com.gabrielfv.biller.home.domain.interfaces.BillsSource
import java.time.Month

class FakeSource : BillsSource {
    var response: Response = Response.EMPTY

    override suspend fun get(): List<BillHistory> {
        return response.value
    }

    enum class Response(val value: List<BillHistory>) {
        EMPTY(listOf<BillHistory>()),
        UNORDERED(listOf(
            BillHistory(
                bill(),
                listOf(
                    payment(2000, Month.JANUARY, 1000),
                    payment(2020, Month.FEBRUARY, 1500),
                    payment(2010, Month.DECEMBER, 2000),
                )
            )
        )),
        STATES(listOf(
            BillHistory(bill(name = "A"), listOf()),
            BillHistory(
                bill(name = "B", expiryDay = 3),
                listOf()
            ),
            BillHistory(
                bill(name = "C", expiryDay = 6),
                listOf()
            ),
            BillHistory(bill(name = "D"), listOf(payment())),
            BillHistory(
                bill(name = "E"),
                listOf(payment(month = Month.JANUARY))
            )
        ))
    }
}

private fun bill(
    name: String = "",
    expiryDay: Int = 1,
    valueInCents: Int? = null
) = Bill(
    name = name,
    expiryDay = expiryDay,
    fixedValue = false,
    valueInCents = valueInCents
)

private fun payment(
    year: Int = 2020,
    month: Month = Month.FEBRUARY,
    valueInCents: Int = 0
) = Payment(
    billId = 0,
    year = year,
    month = month,
    valueInCents = valueInCents
)
