package com.gabrielfv.biller.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bill(
    val id: Int,
    val name: String,
    val valueWhole: String,
    val valueCents: Int,
) : Parcelable