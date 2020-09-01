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