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
package com.gabrielfv.biller.database.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.biller.database.entities.BillHistory
import com.gabrielfv.biller.database.entities.Payment

@Dao
interface BillsDao {

    @Insert
    suspend fun insert(vararg newBill: Bill)

    @Transaction
    @Query("SELECT * FROM bill")
    suspend fun fetchWithHistory(): List<BillHistory>

    @Insert
    suspend fun addPayment(vararg payment: Payment)
}
