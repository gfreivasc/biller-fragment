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
            fixedValue = newBill.fixedValue,
            valueInCents = if (newBill.fixedValue) newBill.value else null
        )
        source.insert(bill)
    }
}