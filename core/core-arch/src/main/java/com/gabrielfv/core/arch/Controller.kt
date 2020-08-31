package com.gabrielfv.core.arch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val STATE_REGISTRY = "com.gabrielfv.core.arch.STATE_REGISTRY"

/**
 * Default controller that uses regular [androidx.fragment.app.Fragment]
 * class as a base to allow for easier integration in a codebase with
 * intended lower friction, since it's easier to implement.
 *
 * This allows client code to leverage existing fragment navigation
 * and instance management solutions, like jetpack navigation.
 */
abstract class Controller<S : Parcelable> : Fragment(), ControllerDefinition<S> {
    private val engine: ControllerDefinition<S> by lazy { ControllerEngine(view) }
    override val state: S get() = engine.state

    /**
     * Initialization of view state and controller logic. It's
     * called every time fragment is initialized without a saved
     * state.
     */
    abstract fun initialize(): S

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
        setState(saved ?: initialize())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_REGISTRY, state)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroy()
    }

    /**
     * This is not supposed to be called since the system is
     * already able to properly handle fragment composition,
     * that is if we'd wish to register this controller within
     * another set to be destroyed for whatever reason.
     */
    override fun destroy() {
        engine.destroy()
    }

    override fun setState(state: S) {
        engine.setState(state)
    }
}
