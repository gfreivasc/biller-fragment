package com.gabrielfv.biller.home.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

class DateTimeSource(
    private val clock: Clock = Clock.System
) {

    fun getMonthAndDay(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Pair<Month, Int> {
        val today = clock.todayAt(timeZone)
        val month = today.month
        val day = today.dayOfMonth

        return Pair(month, day)
    }

    fun getYear(
        timeZone: TimeZone = TimeZone.currentSystemDefault()
    ): Int {
        val today = clock.todayAt(timeZone)
        return today.year
    }
}
