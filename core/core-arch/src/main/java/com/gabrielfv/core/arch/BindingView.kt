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
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

/**
 * Abstraction to make working with Jetpack View Binding (not
 * Data binding) easier and more straight forward. For example,
 * if our view wants to deal with binding instance, it makes
 * little sense to require code that inflates an instance of
 * [android.view.View].
 *
 * We also take the opportunity to turn the API slightly more
 * idiomatic to client code, defining the [onNewState] method
 * on top of [updateState]. Implementors are still required
 * to implement a [bind] method that returns the appropriate
 * instance of [ViewBinding], since we don't have a generic
 * API that does that job for us, since it's static for each
 * of its generated implementations.
 */
abstract class BindingView<B : ViewBinding, S : Parcelable>(
    @LayoutRes val layoutId: Int
) : View<S>, Destroyable {
    private var _binding: B? = null
    val binding get() = _binding!!

    /**
     * Provides a view binding for the inflated [android.view.View]
     * so that we can leverage jetpack API.
     */
    abstract fun bind(view: android.view.View): B

    /**
     * A safe way to listen for state updates without having to
     * worry about possibly nonexistent binding to handle it.
     */
    abstract fun onNewState(state: S)

    final override fun inflate(
        inflater: LayoutInflater,
        root: ViewGroup?
    ): android.view.View {
        controller.registerDestroyable(this)
        val view = inflater.inflate(layoutId, root, false)
        val binding = bind(view)
        _binding = binding
        onStart()
        return binding.root
    }

    /**
     * Abstracts out state updating so that we discard any state
     * that arrives before we're ready to deal with them.
     */
    final override fun updateState(state: S) {
        if (_binding != null) {
            onNewState(state)
        }
    }

    @CallSuper
    override fun destroy() {
        _binding = null
    }
}
