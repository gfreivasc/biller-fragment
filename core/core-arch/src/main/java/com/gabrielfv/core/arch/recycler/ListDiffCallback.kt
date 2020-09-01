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