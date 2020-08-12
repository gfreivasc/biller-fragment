package com.gabrielfv.core.arch.recycler

import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<T>(
    private val items: List<T>
) : RecyclerView.Adapter<BindingListViewHolder<T>>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BindingListViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }
}