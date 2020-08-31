package com.gabrielfv.biller.home.domain.mappers

import com.gabrielfv.biller.home.domain.DateTimeSource
import com.gabrielfv.biller.home.domain.entities.PaymentState
import kotlinx.datetime.Month

class PaymentStateMapper(
    private val dateTimeSource: DateTimeSource = DateTimeSource()
) {

    fun map(lastYear: Int?, lastMonth: Month?, expiryDay: Int): PaymentState {
        val (month, day) = dateTimeSource.getMonthAndDay()
        val year = dateTimeSource.getYear()

        return if (month == lastMonth && year == lastYear) PaymentState.PAID
        else when {
            day > expiryDay -> PaymentState.EXPIRED
            overAMonth(
                lastYear,
                lastMonth,
                year,
                month
            ) -> PaymentState.EXPIRED
            day > (expiryDay - 3) -> PaymentState.TO_BE_EXPIRED
            else -> PaymentState.OPEN
        }
    }

    private fun overAMonth(
        lastYear: Int?,
        lastMonth: Month?,
        currentYear: Int,
        currentMonth: Month
    ): Boolean {
        return if (lastYear == currentYear) currentMonth - 1 > lastMonth
        else lastMonth != Month.DECEMBER || currentMonth != Month.JANUARY
    }
}
