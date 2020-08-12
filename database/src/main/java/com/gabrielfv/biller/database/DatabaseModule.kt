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