package com.gabrielfv.biller.addbill

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddBillState(
    val showValueField: Boolean
) : Parcelable