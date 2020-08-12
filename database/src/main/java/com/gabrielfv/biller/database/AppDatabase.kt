package com.gabrielfv.biller.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrielfv.biller.database.access.BillsDao
import com.gabrielfv.biller.database.entities.Bill

@Database(
    entities = [
        Bill::class,
    ],
    version = 1,
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun billsDao(): BillsDao
}