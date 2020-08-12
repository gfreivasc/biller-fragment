package com.gabrielfv.biller.home.domain

import com.gabrielfv.biller.home.domain.data.FakeBillsSource
import com.gabrielfv.biller.home.domain.entities.Bill
import com.gabrielfv.biller.home.domain.interfaces.BillsSource

class FetchBillsUseCase(
    private val source: BillsSource = FakeBillsSource()
) {

    suspend fun execute(): List<Bill> {
        return source.get()
    }
}