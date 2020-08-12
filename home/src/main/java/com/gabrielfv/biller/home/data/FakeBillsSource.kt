package com.gabrielfv.biller.home.data

import com.gabrielfv.biller.home.domain.entities.Bill
import com.gabrielfv.biller.home.domain.interfaces.BillsSource

class FakeBillsSource : BillsSource {

    override suspend fun get(): List<Bill> {
        return listOf(
            Bill("Water", 1000),
            Bill("Electricity", 2800),
            Bill("Cellular", 1990),
            Bill("Rent", 179940),
            Bill("Car Insurance", 19900),
        )
    }
}