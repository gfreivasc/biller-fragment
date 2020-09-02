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

import android.view.View
import androidx.core.view.isVisible
import com.gabrielfv.biller.home.databinding.HomeViewBinding
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.recycler.ItemInteraction

class HomeView(
    override val controller: HomeController
) : BindingView<HomeViewBinding, HomeState>(R.layout.home_view) {
    private val adapter = BillAdapter(ItemInteraction.Click { bill ->
        controller.billClick(bill)
    })

    override fun bind(view: View): HomeViewBinding {
        return HomeViewBinding.bind(view)
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
