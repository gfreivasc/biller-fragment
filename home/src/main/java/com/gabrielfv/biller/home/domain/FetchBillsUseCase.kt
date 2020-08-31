package com.gabrielfv.biller.home.domain

import com.gabrielfv.biller.database.entities.BillHistory
import com.gabrielfv.biller.home.data.LocalBillsSource
import com.gabrielfv.biller.home.domain.entities.Bill
import com.gabrielfv.biller.home.domain.interfaces.BillsSource
import com.gabrielfv.biller.home.domain.mappers.PaymentStateMapper

class FetchBillsUseCase(
    private val source: BillsSource = LocalBillsSource(),
    private val paymentStateMapper: PaymentStateMapper = PaymentStateMapper()
) {

    suspend fun execute(): List<Bill> {
        return source.get().map(::mapData)
    }

    private fun mapData(billHistory: BillHistory): Bill {
        // There's no guarantee that list's last is actually the latest
        // payment. Probably a good idea to enforce it on DB query (faster).
        val bill = billHistory.bill

        val latestPayment = billHistory.paymentHistory.lastOrNull()
        val paymentState = paymentStateMapper.map(
            latestPayment?.year,
            latestPayment?.month,
            billHistory.bill.expiryDay
        )

        val valueInCents = if (bill.fixedValue) {
            bill.valueInCents
        } else {
            latestPayment?.valueInCents
        }

        return Bill(
            id = bill.id,
            name = bill.name,
            paymentState = paymentState,
            fixedValue = bill.fixedValue,
            valueInCents = valueInCents
        )
    }
}
