package com.gabrielfv.biller.home.mapper

import com.gabrielfv.biller.home.model.Bill

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

const val STEP_LENGTH = 3

class BillMapper {

    fun map(domainBill: DomainBill, divider: Char = ','): Bill {
        val whole = domainBill.valueTimesTen / 10
        val cents = domainBill.valueTimesTen % 10
        return Bill(
            name = domainBill.name,
            valueWhole = formatBig(whole, divider),
            valueCents = cents
        )
    }

    private fun formatBig(number: Int, divider: Char = ',', step: Int = STEP_LENGTH): String {
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