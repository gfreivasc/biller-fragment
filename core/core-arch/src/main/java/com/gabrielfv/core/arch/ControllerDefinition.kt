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
 * Basic functional definitions for Controllers. This should
 * be used as a guideline for creating controller backbones
 * and is not intended for implementation on client code
 * unless the goal is to create a controller abstraction.
 *
 * Each controller must have a state associated with it,
 * that can be derived from a state machine of any nature.
 * It also must have a view it controls that watches the
 * state and notifies it about interactions.
 */
interface ControllerDefinition<S : Parcelable> : Destroyable {
    val view: View<S>
    val initialState: S? get() = null
    val state: S

    fun setState(state: S)

    fun registerDestroyable(vararg destroyable: Destroyable)

    /**
     * Defines a new state for the controller and dispatches
     * it to the view. The callback receives the current state
     * to allow for state evolution logic.
     */
    fun setState(setter: (S) -> S) {
        setState(setter(state))
    }

    /**
     * Responsible for starting the lifecycle of a new controller
     * and setting its initial [state] value.
     */
    fun onInitialize(initialState: S?) {
        if (initialState != null) {
            setState(initialState)
        }
    }
}

internal fun <S : Parcelable> ControllerDefinition<S>.initialize() {
    onInitialize(initialState)
    try {
        state
    } catch (ex: Exception) {
        throw IllegalStateException(
            """
                [${this::class.java.simpleName}.onInitialize()] does not
                guarantee state integrity. Make sure to either set state
                via [setState()] or by calling [super.onInitialize()] with
                a set [initialState]
            """.trimIndent()
        )
    }
}
