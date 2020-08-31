package com.gabrielfv.core.arch

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Defines a view that is tied to a state object that
 * determines how it must be constructed. It is tied
 * to system's default [android.view.View] class and
 * requires inflation as it stands, so it's not a basic
 * definition of a view.
 *
 * Moving part of this definition to a more general and
 * basic one would probably be agnostic of [onStart] and
 * [inflate] methods, but could hold a method to notify
 * controllers of interactions, allowing for greater
 * system decoupling.
 */
interface View<T : Parcelable> {
    val controller: Controller<T>

    fun inflate(inflater: LayoutInflater, root: ViewGroup?): View

    /**
     * Callback for when the view is properly inflated and ready
     * to be provided with dynamic data and listeners.
     */
    fun onStart() = Unit

    /**
     * Callback for whenever the controller notifies that a new
     * state has been set.
     */
    fun updateState(state: T)
}
