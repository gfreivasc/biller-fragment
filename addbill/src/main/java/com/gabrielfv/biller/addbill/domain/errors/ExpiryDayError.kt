package com.gabrielfv.biller.addbill.domain.errors

sealed class ExpiryDayError {
    object NotARecurringMonthDay : ExpiryDayError()
    object NotSafeForFebruary : ExpiryDayError()
}