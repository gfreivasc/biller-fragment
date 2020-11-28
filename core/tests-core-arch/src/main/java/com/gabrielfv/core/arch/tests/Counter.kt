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
package com.gabrielfv.core.arch.tests

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider
import com.gabrielfv.core.arch.tests.databinding.CounterViewBinding
import kotlinx.parcelize.Parcelize

abstract class CounterController : Controller<CounterState>() {

    fun inc() = setState { CounterState(it.count + 1) }

    fun dec() = setState { CounterState(it.count - 1) }
}

class DefaultCounterController(
    viewProvider: ViewProvider<DefaultCounterController, CounterState> =
        ViewProvider { CounterView(it) }
) : CounterController() {
    override val view: View<CounterState> = viewProvider.get(this)
    override val initialState = CounterState(0)
}

class CounterView(
    override val controller: CounterController
) : View<CounterState> {
    lateinit var binding: CounterViewBinding

    override fun inflate(
        inflater: LayoutInflater,
        root: ViewGroup?
    ): android.view.View {
        binding = CounterViewBinding.inflate(inflater, root, false)
        return binding.root
    }

    override fun onStart() {
        binding.incButton.setOnClickListener { controller.inc() }
        binding.decButton.setOnClickListener { controller.dec() }
    }

    override fun updateState(state: CounterState) {
        binding.counter.text = "${state.count}"
    }
}

@Parcelize
data class CounterState(val count: Int) : Parcelable
