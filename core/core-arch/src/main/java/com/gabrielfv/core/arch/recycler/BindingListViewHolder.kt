package com.gabrielfv.core.arch.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingListViewHolder<T>(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    val context = itemView.context

    abstract fun bind(item: T)
}