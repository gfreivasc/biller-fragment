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

import android.view.ViewGroup
import com.gabrielfv.biller.home.databinding.BillItemBinding
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.biller.home.model.Payment
import com.gabrielfv.core.arch.recycler.BindingListAdapter
import com.gabrielfv.core.arch.recycler.BindingListViewHolder
import com.gabrielfv.core.arch.recycler.ItemInteraction
import com.gabrielfv.core.ktx.inflater

class BillAdapter(
    private val onClickItem: ItemInteraction.Click<Bill>
) : BindingListAdapter<Bill>() {

    init {
        setIdentityMatcher { old, new -> old.id == new.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = BillItemBinding.inflate(parent.inflater, parent, false)
        return VH(binding)
    }

    inner class VH(private val binding: BillItemBinding) : BindingListViewHolder<Bill>(binding) {
        override fun bind(item: Bill) = with(binding) {
            name.text = item.name

            value.text = defineValueText(item.payment)
            root.setOnClickListener {
                onClickItem(item)
            }
        }

        private fun defineValueText(payment: Payment?): String {
            return payment?.let { actual ->
                context.getString(
                    R.string.currency_format,
                    actual.valueWhole,
                    actual.valueCents
                )
            } ?: context.getString(R.string.no_payment_found)
        }
    }
}
