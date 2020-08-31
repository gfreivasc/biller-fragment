package com.gabrielfv.biller.addbill.domain

import com.gabrielfv.biller.addbill.data.LocalBillsSource
import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.interfaces.BillsSource
import com.gabrielfv.biller.database.entities.Bill

class AddBillUseCase(
    private val source: BillsSource = LocalBillsSource()
) {

    suspend fun execute(newBill: NewBill) {
        val bill = Bill(
            name = newBill.name,
            expiryDay = newBill.expiryDay,
            fixedValue = newBill.isFixedValue,
            valueInCents = if (newBill.isFixedValue) newBill.fixedValue else null
        )
        source.insert(bill)
    }
}