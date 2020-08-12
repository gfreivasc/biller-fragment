package com.gabrielfv.core.arch.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<T>(
    items: List<T>
) : RecyclerView.Adapter<BindingListViewHolder<T>>() {
    private val items = items.toMutableList()

    fun updateData(new: List<T>) {
        val diffCallback = ListDiffUtil(items, new)
        val diff = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(new)
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BindingListViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }
}