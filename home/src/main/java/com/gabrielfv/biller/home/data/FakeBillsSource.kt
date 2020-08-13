package com.gabrielfv.biller.home.data

import com.gabrielfv.biller.home.domain.entities.Bill
import com.gabrielfv.biller.home.domain.interfaces.BillsSource

class FakeBillsSource : BillsSource {

    override suspend fun get(): List<Bill> {
        return listOf(
            Bill(0, "Water", 1000),
            Bill(1, "Electricity", 2800),
            Bill(2, "Cellular", 1990),
            Bill(3, "Rent", 179940),
            Bill(4, "Car Insurance", 19900),
        )
    }
}