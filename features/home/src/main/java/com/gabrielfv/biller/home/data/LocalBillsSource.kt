package com.gabrielfv.biller.home.data

import com.gabrielfv.biller.database.DatabaseModule
import com.gabrielfv.biller.database.access.BillsDao
import com.gabrielfv.biller.database.entities.BillHistory
import com.gabrielfv.biller.home.domain.interfaces.BillsSource

class LocalBillsSource(
    private val dao: BillsDao = DatabaseModule.provideBillsDao()
) : BillsSource {

    override suspend fun get(): List<BillHistory> {
        return dao.fetchWithHistory()
    }
}
