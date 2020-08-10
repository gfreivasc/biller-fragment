package com.gabrielfv.biller.home

import android.os.Parcelable
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import kotlinx.android.parcel.Parcelize

class HomeController : Controller<HomeState>() {
    override val view: View<HomeState> = HomeView(this)

    override fun initialize() = HomeState(0)

    fun inc() = setState { current -> HomeState(current.count + 1) }

    fun dec() = setState { current -> HomeState(current.count - 1) }
}

@Parcelize
data class HomeState(
    val count: Int
) : Parcelable