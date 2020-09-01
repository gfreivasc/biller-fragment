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
package com.gabrielfv.biller.addbill.data

import com.gabrielfv.biller.addbill.domain.interfaces.BillsSource
import com.gabrielfv.biller.database.DatabaseModule
import com.gabrielfv.biller.database.access.BillsDao
import com.gabrielfv.biller.database.entities.Bill

class LocalBillsSource(
    private val dao: BillsDao = DatabaseModule.provideBillsDao()
) : BillsSource {

    override suspend fun insert(newBill: Bill) {
        dao.insert(newBill)
    }
}