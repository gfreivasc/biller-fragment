package com.gabrielfv.biller.home.domain.data

import com.gabrielfv.biller.home.domain.entities.Bill
import com.gabrielfv.biller.home.domain.interfaces.BillsSource

class FakeBillsSource : BillsSource {

    override suspend fun get(): List<Bill> {
        return listOf(
            Bill("Water", "$ 10.00"),
            Bill("Electricity", "$ 28.00"),
            Bill("Cellular", "$ 19.90"),
            Bill("Rent", "$ 1,799.40"),
            Bill("Car Insurance", "$ 199.00"),
        )
    }
}