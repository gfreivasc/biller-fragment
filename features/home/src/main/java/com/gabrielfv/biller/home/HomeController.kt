/*
 * Copyright 2020 Gabriel Freitas Vasconcelos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabrielfv.biller.home

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
import com.gabrielfv.core.nav.Routes
import com.gabrielfv.core.nav.read

class HomeController(
    private val fetchBillsUseCase: FetchBillsUseCase = FetchBillsUseCase(),
    private val coroutinesExecutor: CoroutinesExecutor = MainCoroutinesExecutor(),
    private val mapper: BillMapper = BillMapper(),
    private val nav: NavRegistry<Routes> = NavManager.getRegistry(),
    viewProvider: ViewProvider<HomeController, HomeState> = ViewProvider { HomeView(it) }
) : Controller<HomeState>() {
    override val view: View<HomeState> = viewProvider.get(this)

    override fun onInitialize(initialState: HomeState?) {
        fetchBills()
    }

    override fun onResume() {
        super.onResume()
        if (nav.read<Boolean>(NavRegistry.BILLS_MODIFIED) == true) {
            fetchBills()
        }
    }

    private fun fetchBills() {
        setState(loadingState())
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
        findNavController().navigate(nav.routes.addBill)
    }

    private fun loadingState() = HomeState(true, listOf())

    private fun loadedState(bills: List<Bill>) = HomeState(false, bills)
}
