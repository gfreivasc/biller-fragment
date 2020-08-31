package com.gabrielfv.core.arch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val STATE_REGISTRY = "com.gabrielfv.core.arch.STATE_REGISTRY"

abstract class Controller<S : Parcelable> : Fragment() {
    abstract val view: View<S>
    abstract val initialState: S
    protected var state: S? = null
        set(value) {
            if (value != null) {
                field = value
                view.updateState(value)
            }
        }
    private val destroyableSet = mutableSetOf<Destroyable>()

    /**
     * Initialization of view state and controller logic. It's
     * called every time fragment is initialized without a saved
     * state.
     */
    protected open fun onStarted() {
        state = initialState
    }

    private fun startAndCheckState() {
        onStarted()
        if (state == null) {
            throw IllegalStateException(
                """
                    ${this::class.java.simpleName}.onStarted() did not start it's state.
                    Make sure its implementation of `.onStarted()` sets the state synchronously,
                    or use the default implementation instead.
                """.trimIndent()
            )
        }
    }

    /**
     * Registers objects with a `.destroy()` routine to be called
     * upon controller's destruction. The same instance can be safely
     * registered more than once, since any call will simply be ignored
     * after the first one.
     */
    fun registerDestroyable(vararg destroyable: Destroyable) {
        destroyableSet.addAll(destroyable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return view.inflate(inflater, container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val saved = savedInstanceState?.getParcelable<S>(STATE_REGISTRY)
        if (saved == null) {
            startAndCheckState()
        } else {
            state = saved
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (state != null) {
            outState.putParcelable(STATE_REGISTRY, state)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyableSet.forEach { it.destroy() }
        destroyableSet.clear()
    }

    /**
     * Defines a new state for the controller and dispatches
     * it to the view. The callback receives the current state
     * to allow for state evolution logic.
     */
    protected inline fun setState(setter: (S) -> S) {
        state = setter(state ?: initialState)
    }
}