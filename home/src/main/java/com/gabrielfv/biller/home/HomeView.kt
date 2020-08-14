package com.gabrielfv.biller.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.gabrielfv.biller.home.databinding.HomeViewBinding
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.recycler.ItemInteraction

class HomeView(
    override val controller: HomeController
) : BindingView<HomeViewBinding, HomeState>(controller) {
    private val adapter = BillAdapter(ItemInteraction.Click { bill ->
        controller.billClick(bill)
    })

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): HomeViewBinding {
        return HomeViewBinding.inflate(inflater, container, false)
    }

    override fun onStart() = with(binding) {
        recyclerView.adapter = adapter
        addBill.setOnClickListener { controller.addBill() }
    }

    override fun onNewState(state: HomeState) = with(binding) {
        progressBar.isVisible = state.loading
        recyclerView.isVisible = !state.loading
        if (!state.loading) {
            adapter.updateData(state.bills)
        }
    }
}