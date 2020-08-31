package com.gabrielfv.biller.home.model

import android.os.Parcelable
import com.gabrielfv.biller.home.domain.entities.PaymentState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bill(
    val id: Long,
    val name: String,
    val payment: Payment,
    val state: PaymentState,
) : Parcelable

sealed class Payment : Parcelable {
    @Parcelize
    data class Value(
        val valueWhole: String,
        val valueCents: Int,
    ) : Payment()

    @Parcelize
    object None : Payment()
}
