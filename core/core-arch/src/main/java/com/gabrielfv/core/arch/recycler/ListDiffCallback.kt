package com.gabrielfv.core.arch.recycler

import androidx.recyclerview.widget.DiffUtil

class ListDiffCallback<T>(
    private val old: List<T>,
    private val new: List<T>,
    private val identityMatcher: IdentityMatcher<T> = IdentityMatcher { a, b -> a == b}
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return identityMatcher.matches(old[oldItemPosition], new[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }

    /**
     * This is used to check if two items represent the same entity
     * even though they've possibly changed in contents. For example,
     * if an item represents an action to be completed and the list is
     * updated with that action completed, the will be different whereas
     * the identity is the same. Here we would check for something like
     * 'id' equality.
     */
    fun interface IdentityMatcher<T> {
        fun matches(old: T, new: T): Boolean
    }
}