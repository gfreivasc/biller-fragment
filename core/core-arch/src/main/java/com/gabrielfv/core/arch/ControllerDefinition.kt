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
 * Basic functional definitions for Controllers.
 *
 * Each controller must have a state associated with it,
 * that can be derived from a state machine of any nature.
 * It also must have a view it controls that watches the
 * state and notifies it about interactions.
 */
interface ControllerDefinition<S : Parcelable> : Destroyable {
    val view: View<S>
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
}
