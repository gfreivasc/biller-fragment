package com.gabrielfv.core.arch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val STATE_REGISTRY = "com.gabrielfv.core.arch.STATE_REGISTRY"

abstract class Controller<T : Parcelable> : Fragment() {
    abstract val view: View<T>
    private var state: T? = null
        set(value) {
            if (value != null) {
                field = value
                view.updateState(value)
            }
        }
    private val destroyableSet = mutableSetOf<Destroyable>()

    abstract fun initialize(): T

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
        val saved = savedInstanceState?.getParcelable<T>(STATE_REGISTRY)
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

    protected fun setState(setter: (T) -> T) {
        state = setter(state ?: initialize())
    }
}