package com.gabrielfv.biller.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabrielfv.biller.database.access.BillsDao
import com.gabrielfv.biller.database.converters.MonthConverter
import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.biller.database.entities.Payment

@Database(
    entities = [
        Bill::class,
        Payment::class
    ],
    version = 1,
)
@TypeConverters(MonthConverter::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun billsDao(): BillsDao
}
