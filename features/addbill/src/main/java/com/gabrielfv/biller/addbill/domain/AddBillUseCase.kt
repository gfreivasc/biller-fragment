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

import com.gabrielfv.biller.addbill.data.LocalBillsSource
import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.interfaces.BillsSource
import com.gabrielfv.biller.database.entities.Bill

class AddBillUseCase(
    private val source: BillsSource = LocalBillsSource()
) {

    suspend fun execute(newBill: NewBill) {
        val bill = Bill(
            name = newBill.name,
            expiryDay = newBill.expiryDay,
            fixedValue = newBill.isFixedValue,
            valueInCents = if (newBill.isFixedValue) newBill.fixedValue else null
        )
        source.insert(bill)
    }
}