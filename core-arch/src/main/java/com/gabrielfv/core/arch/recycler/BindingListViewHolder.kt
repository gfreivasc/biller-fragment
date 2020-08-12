package com.gabrielfv.core.arch.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingListViewHolder<T>(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T)
}