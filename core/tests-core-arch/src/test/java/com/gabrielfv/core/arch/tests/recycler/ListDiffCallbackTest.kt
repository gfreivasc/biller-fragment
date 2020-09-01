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
package com.gabrielfv.core.arch.tests.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.gabrielfv.core.arch.recycler.ListDiffCallback
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ListDiffCallbackTest {
    private val mockUpdateCallback = mockk<ListUpdateCallback>(relaxed = true)

    @Test
    fun insertionIsEvaluated() {
        val old = listOf<Int>()
        val new = listOf(1, 2)
        val subject = ListDiffCallback(old, new)

        DiffUtil.calculateDiff(subject)
            .dispatchUpdatesTo(mockUpdateCallback)

        verify { mockUpdateCallback.onInserted(eq(0), eq(2)) }
    }

    @Test
    fun removingIsEvaluated() {
        val old = listOf(1, 2)
        val new = listOf<Int>()
        val subject = ListDiffCallback(old, new)

        DiffUtil.calculateDiff(subject)
            .dispatchUpdatesTo(mockUpdateCallback)

        verify { mockUpdateCallback.onRemoved(eq(0), eq(2)) }
    }

    @Test
    fun movingIsEvaluated() {
        val old = listOf(1, 2, 3)
        val new = listOf(1, 3, 2)
        val subject = ListDiffCallback(old, new)

        DiffUtil.calculateDiff(subject)
            .dispatchUpdatesTo(mockUpdateCallback)

        verify {
            mockUpdateCallback.onMoved(eq(2), eq(1))
        }
    }

    @Test
    fun changingValueIsEvaluated() {
        val old = listOf(IntWithId(0, 1), IntWithId(1, 2))
        val new = listOf(IntWithId(0, 1), IntWithId(1, 3))
        val subject = ListDiffCallback(old, new) { a, b -> a.id == b.id }

        DiffUtil.calculateDiff(subject)
            .dispatchUpdatesTo(mockUpdateCallback)

        verify {
            mockUpdateCallback.onChanged(eq(1), eq(1), isNull())
        }
    }

    @Test
    fun noChangeDoesNotIssueAnUpdate() {
        val old = listOf<Int>()
        val new = listOf<Int>()
        val subject = ListDiffCallback(old, new)

        DiffUtil.calculateDiff(subject)
            .dispatchUpdatesTo(mockUpdateCallback)

        verify(exactly = 0) { mockUpdateCallback.onInserted(any(), any()) }
        verify(exactly = 0) { mockUpdateCallback.onChanged(any(), any(), any()) }
        verify(exactly = 0) { mockUpdateCallback.onMoved(any(), any()) }
        verify(exactly = 0) { mockUpdateCallback.onRemoved(any(), any()) }
    }

    data class IntWithId(val id: Int, val value: Int)
}