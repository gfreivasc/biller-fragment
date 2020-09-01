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

import android.content.Context
import androidx.room.Room

private const val DB_NAME = "biller_db"

object DatabaseModule {
    private lateinit var database: AppDatabase

    fun initDatabase(context: Context) {
        if (::database.isInitialized.not()) {
            database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }

    fun provideBillsDao() = provideDao { billsDao() }

    private fun <T> provideDao(provider: AppDatabase.() -> T): T = try {
        database.provider()
    } catch (e: UninitializedPropertyAccessException) {
        throw IllegalStateException(
            """Attempted to access Dao without initialized database.
               Make sure to call `DatabaseModule::initDatabase(context)` before requesting a Dao. 
            """.trimIndent()
        )
    }
}