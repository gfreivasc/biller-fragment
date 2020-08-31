package com.gabrielfv.biller.home.mapper

import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.biller.home.model.Payment

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

const val STEP_LENGTH = 3
const val CENTS_MULTIPLIER = 100
const val US_DIVIDER = ','

class BillMapper {

    fun map(domainBill: DomainBill, divider: Char = US_DIVIDER): Bill {
        val payment = if (domainBill.valueInCents != null) {
            mapPaymentValue(domainBill.valueInCents, divider)
        } else {
            Payment.None
        }

        return Bill(
            id = domainBill.id,
            name = domainBill.name,
            payment = payment,
            state = domainBill.paymentState
        )
    }

    private fun mapPaymentValue(valueInCents: Int, divider: Char): Payment.Value {
        val whole = valueInCents / CENTS_MULTIPLIER
        val cents = valueInCents % CENTS_MULTIPLIER

        return Payment.Value(
            valueWhole = formatBig(whole, divider),
            valueCents = cents
        )
    }

    private fun formatBig(
        number: Int,
        divider: Char = US_DIVIDER,
        step: Int = STEP_LENGTH
    ): String {
        val buffer = number.toString()
        val size = buffer.length

        val builder = StringBuilder()
        buffer.forEachIndexed { index, c ->
            val pos = (size - index)
            val isStep = pos > 0 && pos % step == 0
            if (index > 0 && isStep) {
                builder.append(divider)
            }
            builder.append(c)
        }

        return builder.toString()
    }
}