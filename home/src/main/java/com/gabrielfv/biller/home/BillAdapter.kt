package com.gabrielfv.biller.home

import android.view.ViewGroup
import com.gabrielfv.biller.home.databinding.BillItemBinding
import com.gabrielfv.biller.home.model.Bill
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
            value.text = context.getString(
                R.string.currency_format,
                item.valueWhole,
                item.valueCents
            )
            root.setOnClickListener {
                onClickItem(item)
            }
        }
    }
}