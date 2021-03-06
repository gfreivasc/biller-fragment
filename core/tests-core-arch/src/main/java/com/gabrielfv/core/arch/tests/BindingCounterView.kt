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

import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.tests.databinding.CounterViewBinding

class BindingCounterView(
    override val controller: DefaultCounterController
) : BindingView<CounterViewBinding, CounterState>(R.layout.counter_view) {

    override fun bind(view: android.view.View): CounterViewBinding {
        return CounterViewBinding.bind(view)
    }

    override fun onStart() {
        binding.incButton.setOnClickListener { controller.inc() }
        binding.decButton.setOnClickListener { controller.dec() }
    }

    override fun onNewState(state: CounterState) {
        binding.counter.text = "${state.count}"
    }
}
