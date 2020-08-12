package com.gabrielfv.biller.home.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bill(
    val name: String,
    val value: String
) : Parcelable