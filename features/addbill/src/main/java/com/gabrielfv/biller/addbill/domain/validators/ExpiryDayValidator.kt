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