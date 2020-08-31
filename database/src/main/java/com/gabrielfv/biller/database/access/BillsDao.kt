package com.gabrielfv.biller.database.access

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.biller.database.entities.BillHistory

@Dao
interface BillsDao {

    @Query("SELECT * FROM bill")
    suspend fun fetch(): List<Bill>

    @Insert
    suspend fun insert(vararg newBill: Bill)

    @Transaction
    @Query("SELECT * FROM bill")
    suspend fun fetchWithHistory(): List<BillHistory>
}