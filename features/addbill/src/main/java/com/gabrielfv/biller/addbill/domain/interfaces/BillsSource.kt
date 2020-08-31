package com.gabrielfv.biller.addbill.domain.interfaces

import com.gabrielfv.biller.database.entities.Bill

interface BillsSource {

    suspend fun insert(newBill: Bill)
}