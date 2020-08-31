package com.gabrielfv.biller.home

import android.os.Parcelable
import com.gabrielfv.biller.home.model.Bill
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeState(
    val loading: Boolean,
    val bills: List<Bill>
) : Parcelable
