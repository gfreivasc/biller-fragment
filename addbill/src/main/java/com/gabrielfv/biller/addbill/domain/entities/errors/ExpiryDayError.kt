package com.gabrielfv.biller.addbill.domain.entities.errors

sealed class ExpiryDayError {
    object NotARecurringMonthDay : ExpiryDayError()
    object NotSafeForFebruary : ExpiryDayError()
}