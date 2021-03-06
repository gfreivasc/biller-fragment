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

import com.gabrielfv.biller.database.entities.BillHistory
import com.gabrielfv.biller.database.entities.Payment
import com.gabrielfv.biller.home.data.LocalBillsSource
import com.gabrielfv.biller.home.domain.entities.Bill
import com.gabrielfv.biller.home.domain.interfaces.BillsSource
import com.gabrielfv.biller.home.domain.mappers.PaymentStateMapper

class FetchBillsUseCase(
    private val source: BillsSource = LocalBillsSource(),
    private val paymentStateMapper: PaymentStateMapper = PaymentStateMapper()
) {

    suspend fun execute(): List<Bill> {
        return source.get().map(::mapData)
    }

    private fun mapData(billHistory: BillHistory): Bill {
        val bill = billHistory.bill

        val latestPayment = getLatestPayment(billHistory)
        val paymentState = paymentStateMapper.map(
            latestPayment?.date,
            billHistory.bill.expiryDay,
            bill.registeredAt
        )

        val valueInCents = if (bill.fixedValue) {
            bill.valueInCents
        } else {
            latestPayment?.valueInCents
        }

        return Bill(
            id = bill.id,
            name = bill.name,
            paymentState = paymentState,
            fixedValue = bill.fixedValue,
            valueInCents = valueInCents,
        )
    }

    private fun getLatestPayment(billHistory: BillHistory): Payment? {
        return billHistory.paymentHistory.maxOrNull()
    }
}
