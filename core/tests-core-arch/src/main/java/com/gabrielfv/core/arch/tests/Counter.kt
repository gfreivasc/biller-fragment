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
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.counter_view.view.*

abstract class CounterController : Controller<CounterState>() {

    fun inc() = setState { CounterState(it.count + 1) }

    fun dec() = setState { CounterState(it.count - 1) }
}

class CounterView(
    override val controller: CounterController
) : View<CounterState> {
    lateinit var view: android.view.View

    override fun inflate(
        inflater: LayoutInflater,
        root: ViewGroup?
    ): android.view.View {
        return inflater
                .inflate(R.layout.counter_view, root, false)
                .also { view = it }
    }

    override fun onStart() {
        view.incButton.setOnClickListener { controller.inc() }
        view.decButton.setOnClickListener { controller.dec() }
    }

    override fun updateState(state: CounterState) {
        view.counter.text = "${state.count}"
    }
}

@Parcelize
data class CounterState(val count: Int) : Parcelable

fun <C : CounterController> C.providerFor(): ViewProvider<C, CounterState> =
    ViewProvider { controller -> CounterView(controller) }
