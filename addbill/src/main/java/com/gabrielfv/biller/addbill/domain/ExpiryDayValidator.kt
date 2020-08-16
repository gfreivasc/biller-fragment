package com.gabrielfv.biller.addbill.domain

import com.gabrielfv.biller.addbill.domain.errors.ExpiryDayError

private const val NOT_SAFE_FOR_FEB = 29
private const val NOT_A_RECURRING_MONTH_DAY = 31

class ExpiryDayValidator {

    fun execute(day: Int): Either<ExpiryDayError, Boolean> = when {
        !isRecurringMonthDay(day) -> left(ExpiryDayError.NotARecurringMonthDay)
        day >= NOT_SAFE_FOR_FEB -> left(ExpiryDayError.NotSafeForFebruary)
        else -> right(true)
    }

    private fun isRecurringMonthDay(day: Int) = day in 1 until NOT_A_RECURRING_MONTH_DAY
}