/*
 * Copyright 2020 Gabriel Freitas Vasconcelos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
