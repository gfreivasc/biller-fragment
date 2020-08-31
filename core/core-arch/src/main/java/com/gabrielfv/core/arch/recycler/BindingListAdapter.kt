package com.gabrielfv.core.arch.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<T> : RecyclerView.Adapter<BindingListViewHolder<T>>() {
    private val items = mutableListOf<T>()
    private var identityMatcher: ListDiffCallback.IdentityMatcher<T>? = null

    fun updateData(new: List<T>) {
        val diffCallback = buildDiffCallback(new)
        val diff = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(new)
        diff.dispatchUpdatesTo(this)
    }

    fun setIdentityMatcher(identityMatcher: ListDiffCallback.IdentityMatcher<T>?) {
        this.identityMatcher = identityMatcher
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BindingListViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    private fun buildDiffCallback(new: List<T>): ListDiffCallback<T> {
        return identityMatcher?.let { matcher ->
            ListDiffCallback(items, new, matcher)
        } ?: ListDiffCallback(items, new)
    }
}