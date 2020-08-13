package com.gabrielfv.biller.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.gabrielfv.biller.home.databinding.HomeViewBinding
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.recycler.ItemInteraction

class HomeView(
    override val controller: HomeController
) : BindingView<HomeViewBinding, HomeState>(controller) {
    private lateinit var adapter: BillAdapter

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): HomeViewBinding {
        return HomeViewBinding.inflate(inflater, container, false)
    }

    override fun onNewState(state: HomeState) = with(binding) {
        progressBar.isVisible = state.loading
        recyclerView.isVisible = !state.loading
        if (!state.loading) {
            recyclerView.adapter = manageAdapter(state.bills)
        }
    }

    private fun manageAdapter(bills: List<Bill>): BillAdapter {
        if (::adapter.isInitialized.not()) {
            val adapter = BillAdapter(bills, ItemInteraction.Click { bill ->
                controller.billClick(bill)
            })
            this.adapter = adapter
        } else {
            adapter.updateData(bills)
        }

        return adapter
    }
}