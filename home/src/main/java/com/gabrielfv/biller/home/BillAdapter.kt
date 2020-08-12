package com.gabrielfv.biller.home

import android.view.ViewGroup
import com.gabrielfv.biller.home.databinding.BillItemBinding
import com.gabrielfv.core.arch.recycler.BindingListAdapter
import com.gabrielfv.core.arch.recycler.BindingListViewHolder
import com.gabrielfv.core.ktx.inflater

class BillAdapter(
    bills: List<Bill>
) : BindingListAdapter<Bill>(bills) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = BillItemBinding.inflate(parent.inflater, parent, false)
        return VH(binding)
    }

    class VH(private val binding: BillItemBinding) : BindingListViewHolder<Bill>(binding) {
        override fun bind(item: Bill) {
            binding.name.text = item.name
            binding.value.text = item.value
        }
    }
}