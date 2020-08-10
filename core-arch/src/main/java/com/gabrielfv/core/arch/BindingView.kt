package com.gabrielfv.core.arch

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BindingView<Binding, T : Parcelable>(
    override val controller: Controller<T>
) : View<T>, Destroyable {
    private var _binding: Binding? = null
    val binding get() = _binding!!

    abstract fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binder: (Binding) -> Unit
    ): android.view.View

    abstract fun onNewState(state: T)

    override fun inflate(inflater: LayoutInflater, root: ViewGroup?): android.view.View {
        controller.registerDestroyable(this)
        return bind(inflater, root) { _binding = it }
    }

    override fun updateState(state: T) {
        if (_binding != null) {
            onNewState(state)
        }
    }

    override fun destroy() {
        _binding = null
    }
}