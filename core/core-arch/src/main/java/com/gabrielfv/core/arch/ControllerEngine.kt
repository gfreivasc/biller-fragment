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
package com.gabrielfv.core.arch

import android.os.Parcelable

/**
 * General implementation of logic engine that powers
 * controller API. It handles state management and
 * [View] communication, along with logic to prevent
 * memory leaking.
 */
internal class ControllerEngine<S : Parcelable>(
    override val view: View<S>,
) : ControllerDefinition<S> {
    private var _state: S? = null
        set(value) {
            if (value != null) {
                field = value
                view.updateState(value)
            }
        }
    override val state: S
        get() = _state
            ?: throw IllegalStateException("""
                Attempted to access controller state prior to setting it.
                Make sure that your state is set before trying to access it.
            """.trimIndent())

    override fun setState(state: S) {
        _state = state
    }

    private val destroyableSet = mutableSetOf<Destroyable>()

    override fun registerDestroyable(vararg destroyable: Destroyable) {
        destroyableSet.addAll(destroyable)
    }

    override fun destroy() {
        destroyableSet.forEach { it.destroy() }
        destroyableSet.clear()
    }
}
