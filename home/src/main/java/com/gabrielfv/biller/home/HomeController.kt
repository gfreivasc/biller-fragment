package com.gabrielfv.biller.home

import android.os.Parcelable
import com.gabrielfv.biller.home.domain.FetchBillsUseCase
import com.gabrielfv.biller.home.mapper.BillMapper
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.CoroutinesExecutor
import com.gabrielfv.core.arch.coroutines.MainCoroutinesExecutor
import com.gabrielfv.core.arch.extras.ViewProvider
import kotlinx.android.parcel.Parcelize

class HomeController(
    private val fetchBillsUseCase: FetchBillsUseCase = FetchBillsUseCase(),
    private val coroutinesExecutor: CoroutinesExecutor = MainCoroutinesExecutor(),
    private val mapper: BillMapper = BillMapper(),
    viewProvider: ViewProvider<HomeController, HomeState> = ViewProvider { HomeView(it) },
) : Controller<HomeState>() {
    override val view: View<HomeState> = viewProvider.get(this)

    override fun initialize() = loadingState()

    override fun onResume() {
        super.onResume()
        setState { loadingState() }
        coroutinesExecutor.execute {
            val bills = fetchBillsUseCase
                .execute()
                .map { mapper.map(it) }
            setState { loadedState(bills) }
        }
        registerDestroyable(coroutinesExecutor)
    }

    fun billClick(bill: Bill) = Unit

    private fun loadingState() = HomeState(true, listOf())

    private fun loadedState(bills: List<Bill>) = HomeState(false, bills)
}

@Parcelize
data class HomeState(
    val loading: Boolean,
    val bills: List<Bill>
) : Parcelable