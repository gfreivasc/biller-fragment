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