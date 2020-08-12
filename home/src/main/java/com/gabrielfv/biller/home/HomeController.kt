package com.gabrielfv.biller.home

import android.os.Parcelable
import android.util.Log
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider
import kotlinx.android.parcel.Parcelize

class HomeController(
    viewProvider: ViewProvider<HomeController, HomeState> = ViewProvider { HomeView(it) }
) : Controller<HomeState>() {
    override val view: View<HomeState> = viewProvider.get(this)
    private val bills = mutableListOf(
        Bill("Water", "$ 10.00"),
        Bill("Electricity", "$ 28.00"),
        Bill("Cellular", "$ 19.90"),
        Bill("Rent", "$ 1,799.40"),
        Bill("Car Insurance", "$ 199.00"),
    )

    override fun initialize() = HomeState(false, bills)

    fun billClick(bill: Bill) {
        bills.add(Bill("${bill.name}+", bill.value))
        setState { HomeState(false, bills) }
    }
}

@Parcelize
data class HomeState(
    val loading: Boolean,
    val bills: List<Bill>
) : Parcelable