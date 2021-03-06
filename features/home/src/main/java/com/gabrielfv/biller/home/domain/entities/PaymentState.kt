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
package com.gabrielfv.biller.home.domain.entities

import android.os.Parcelable
import com.gabrielfv.core.ktx.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import kotlinx.datetime.LocalDate

@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>
data class PaymentState(
    val lastDate: LocalDate,
    val state: State
) : Parcelable {
    enum class State {
        OPEN,
        TO_BE_EXPIRED,
        EXPIRED,
        FORGOTTEN,
        PAID
    }
}
