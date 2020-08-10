package com.gabrielfv.biller.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabrielfv.biller.home.databinding.HomeViewBinding
import com.gabrielfv.core.arch.BindingView

class HomeView(
    override val controller: HomeController
) : BindingView<HomeViewBinding, HomeState>(controller) {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binder: (HomeViewBinding) -> Unit
    ): View {
        val binding = HomeViewBinding.inflate(inflater, container, false)
        binder(binding)
        setListeners()
        return binding.root
    }

    override fun onNewState(state: HomeState) {
        binding.counter.text = state.count.toString()
    }

    private fun setListeners() {
        binding.incButton.setOnClickListener {
            controller.inc()
        }

        binding.decButton.setOnClickListener {
            controller.dec()
        }
    }
}