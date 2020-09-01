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
package com.gabrielfv.biller.home.model

import android.os.Parcelable
import com.gabrielfv.biller.home.domain.entities.PaymentState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bill(
    val id: Long,
    val name: String,
    val payment: Payment?,
    val state: PaymentState,
) : Parcelable

@Parcelize
data class Payment(
    val valueWhole: String,
    val valueCents: Int,
) : Parcelable
