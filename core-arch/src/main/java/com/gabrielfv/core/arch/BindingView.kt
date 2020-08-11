package com.gabrielfv.core.arch

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BindingView<B : ViewBinding, S : Parcelable>(
    override val controller: Controller<S>
) : View<S>, Destroyable {
    private var _binding: B? = null
    val binding get() = _binding!!

    abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun onNewState(state: S)

    override fun inflate(inflater: LayoutInflater, root: ViewGroup?): android.view.View {
        controller.registerDestroyable(this)
        val binding = bind(inflater, root)
        _binding = binding
        onStart()
        return binding.root
    }

    override fun updateState(state: S) {
        if (_binding != null) {
            onNewState(state)
        }
    }

    override fun destroy() {
        _binding = null
    }
}