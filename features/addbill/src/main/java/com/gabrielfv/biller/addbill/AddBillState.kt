package com.gabrielfv.biller.addbill

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddBillState(
    val showValueField: Boolean,
    val expiryDayError: String?,
    val nameError: String?,
    val fixedValueError: String?
) : Parcelable