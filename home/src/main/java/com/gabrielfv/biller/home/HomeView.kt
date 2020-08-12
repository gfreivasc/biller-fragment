package com.gabrielfv.biller.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.gabrielfv.biller.home.databinding.HomeViewBinding
import com.gabrielfv.core.arch.BindingView

class HomeView(
    override val controller: HomeController
) : BindingView<HomeViewBinding, HomeState>(controller) {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): HomeViewBinding {
        return HomeViewBinding.inflate(inflater, container, false)
    }

    override fun onNewState(state: HomeState) {
        binding.progressBar.isVisible = state.loading
        binding.recyclerView.isVisible = !state.loading
        if (!state.loading) {
            binding.recyclerView.adapter = BillAdapter(state.bills)
        }
    }
}