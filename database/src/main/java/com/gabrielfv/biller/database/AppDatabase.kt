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
package com.gabrielfv.biller.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabrielfv.biller.database.access.BillsDao
import com.gabrielfv.biller.database.converters.LocalDateConverter
import com.gabrielfv.biller.database.converters.MonthConverter
import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.biller.database.entities.Payment

@Database(
    entities = [
        Bill::class,
        Payment::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [
    MonthConverter::class,
    LocalDateConverter::class
])
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun billsDao(): BillsDao
}
