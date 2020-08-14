package com.gabrielfv.biller.home

import androidx.navigation.fragment.findNavController
import com.gabrielfv.biller.home.domain.FetchBillsUseCase
import com.gabrielfv.biller.home.mapper.BillMapper
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.CoroutinesExecutor
import com.gabrielfv.core.arch.coroutines.MainCoroutinesExecutor
import com.gabrielfv.core.arch.extras.ViewProvider
import com.gabrielfv.core.nav.NavManager
import com.gabrielfv.core.nav.NavRegistry

class HomeController(
    private val fetchBillsUseCase: FetchBillsUseCase = FetchBillsUseCase(),
    private val coroutinesExecutor: CoroutinesExecutor = MainCoroutinesExecutor(),
    private val mapper: BillMapper = BillMapper(),
    private val navManager: NavManager = NavManager,
    viewProvider: ViewProvider<HomeController, HomeState> = ViewProvider { HomeView(it) },
) : Controller<HomeState>() {
    override val view: View<HomeState> = viewProvider.get(this)

    override fun initialize() = loadingState()

    override fun onResume() {
        super.onResume()
        if (navManager.read<Boolean>(NavRegistry.BILLS_MODIFIED) == true) {
            fetchBills()
        }
    }

    private fun fetchBills() {
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

    fun addBill() {
        findNavController().navigate(navManager.routes.addBill)
    }

    private fun loadingState() = HomeState(true, listOf())

    private fun loadedState(bills: List<Bill>) = HomeState(false, bills)
}
