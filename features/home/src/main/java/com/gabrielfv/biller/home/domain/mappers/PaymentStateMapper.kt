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
        return if (lastYear == null || lastMonth == null) false
        else if (lastYear == currentYear) currentMonth - 1 > lastMonth
        else lastMonth != Month.DECEMBER || currentMonth != Month.JANUARY
    }
}
