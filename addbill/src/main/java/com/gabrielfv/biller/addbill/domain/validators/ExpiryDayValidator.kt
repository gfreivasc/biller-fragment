package com.gabrielfv.biller.addbill.domain.validators

import com.gabrielfv.biller.addbill.domain.Either
import com.gabrielfv.biller.addbill.domain.entities.errors.ExpiryDayError
import com.gabrielfv.biller.addbill.domain.left
import com.gabrielfv.biller.addbill.domain.right

private const val NOT_SAFE_FOR_FEB = 29
private const val NOT_A_RECURRING_MONTH_DAY = 31

class ExpiryDayValidator {

    fun execute(day: Int): Either<ExpiryDayError, Unit> = when {
        !isRecurringMonthDay(day) -> left(ExpiryDayError.NotARecurringMonthDay)
        day >= NOT_SAFE_FOR_FEB -> left(ExpiryDayError.NotSafeForFebruary)
        else -> right(Unit)
    }

    private fun isRecurringMonthDay(day: Int) = day in 1 until NOT_A_RECURRING_MONTH_DAY
}