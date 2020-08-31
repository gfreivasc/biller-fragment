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
