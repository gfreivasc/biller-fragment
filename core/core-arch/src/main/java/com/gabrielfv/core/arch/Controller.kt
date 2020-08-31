package com.gabrielfv.core.arch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val STATE_REGISTRY = "com.gabrielfv.core.arch.STATE_REGISTRY"

abstract class Controller<S : Parcelable> : Fragment(), ControllerDefinition<S> {
    private val engine: ControllerDefinition<S> by lazy { ControllerEngine(view) }
    override val state: S get() = engine.state

    /**
     * Initialization of view state and controller logic. It's
     * called every time fragment is initialized without a saved
     * state.
     */
    abstract fun onStarted(): S

    /**
     * Registers objects with a `.destroy()` routine to be called
     * upon controller's destruction. The same instance can be safely
     * registered more than once, since any call will simply be ignored
     * after the first one.
     */
    override fun registerDestroyable(vararg destroyable: Destroyable) {
        engine.registerDestroyable(*destroyable)
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
        setState(saved ?: onStarted())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_REGISTRY, state)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroy()
    }

    override fun destroy() {
        engine.destroy()
    }

    override fun setState(state: S) {
        engine.setState(state)
    }

    /**
     * Defines a new state for the controller and dispatches
     * it to the view. The callback receives the current state
     * to allow for state evolution logic.
     */
    fun setState(setter: (S) -> S) {
        setState(setter(state))
    }
}