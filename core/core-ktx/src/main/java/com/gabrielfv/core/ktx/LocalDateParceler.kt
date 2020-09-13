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
package com.gabrielfv.core.ktx

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

object LocalDateParceler : Parceler<LocalDate> {
    override fun create(parcel: Parcel): LocalDate {
        return parcel.readString()!!.toLocalDate()
    }

    override fun LocalDate.write(parcel: Parcel, flags: Int) {
        parcel.writeString(toString())
    }
}
