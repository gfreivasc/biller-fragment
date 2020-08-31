package com.gabrielfv.biller.home.domain.interfaces

import com.gabrielfv.biller.database.entities.BillHistory

interface BillsSource {
    suspend fun get(): List<BillHistory>
}
