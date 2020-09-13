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

import com.gabrielfv.biller.home.domain.entities.PaymentState
import kotlinx.datetime.*

private const val MONTH_THRESHOLD = 2

class PaymentStateMapper(
    private val clock: Clock = Clock.System,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault()
) {

    fun map(
        lastDate: LocalDate?,
        expiryDay: Int,
        registrationDate: LocalDate
    ): PaymentState {
        val current = clock.now()
                .toLocalDateTime(timeZone)
                .date

        val state = when {
            sameMonth(lastDate, current) -> PaymentState.State.PAID
            lastDate == null && forgottenSinceRegistration(
                registrationDate,
                current
            ) -> PaymentState.State.FORGOTTEN
            current.dayOfMonth > expiryDay -> PaymentState.State.EXPIRED
            overAMonth(lastDate, current) -> PaymentState.State.EXPIRED
            current.dayOfMonth > (expiryDay - 3) ->
                PaymentState.State.TO_BE_EXPIRED
            else -> PaymentState.State.OPEN
        }

        return PaymentState(
            lastDate ?: registrationDate,
            state
        )
    }

    private fun sameMonth(
        lastDate: LocalDate?,
        currentDate: LocalDate
    ): Boolean {
        return lastDate?.monthsUntil(currentDate) == 0
    }

    private fun overAMonth(
        lastDate: LocalDate?,
        currentDate: LocalDate
    ): Boolean {
        return when {
            lastDate == null -> false
            lastDate.year == currentDate.year -> {
                currentDate.month - 1 > lastDate.month
            }
            else -> {
                lastDate.month != Month.DECEMBER ||
                        currentDate.month != Month.JANUARY
            }
        }
    }

    private fun forgottenSinceRegistration(
        registrationDate: LocalDate,
        currentDate: LocalDate
    ): Boolean {
        return registrationDate.monthsUntil(currentDate) > MONTH_THRESHOLD
    }
}
