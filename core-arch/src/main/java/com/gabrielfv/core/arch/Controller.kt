package com.gabrielfv.core.arch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val STATE_REGISTRY = "com.gabrielfv.core.arch.STATE_REGISTRY"

abstract class Controller<S : Parcelable> : Fragment() {
    abstract val view: View<S>
    private var state: S? = null
        set(value) {
            if (value != null) {
                field = value
                view.updateState(value)
            }
        }
    private val destroyableSet = mutableSetOf<Destroyable>()

    abstract fun initialize(): S

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
        state = saved ?: initialize()
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

    protected fun setState(setter: (S) -> S) {
        state = setter(state ?: initialize())
    }
}