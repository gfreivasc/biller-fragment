package com.gabrielfv.core.arch.tests

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider
import com.gabrielfv.core.arch.tests.databinding.CounterViewBinding
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterState(val count: Int) : Parcelable

class CounterController(
    viewProvider: ViewProvider<CounterController, CounterState> = ViewProvider { CounterView(it) }
) : Controller<CounterState>() {
    override val view: View<CounterState> = viewProvider.get(this)

    override fun initialize() = CounterState(0)

    fun inc() = setState { CounterState(it.count + 1) }

    fun dec() = setState { CounterState(it.count - 1) }
}

class CounterView(
    override val controller: CounterController
) : BindingView<CounterViewBinding, CounterState>(controller) {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): CounterViewBinding {
        return CounterViewBinding.inflate(inflater, container, false)
    }

    override fun onStart() {
        binding.incButton.setOnClickListener { controller.inc() }
        binding.decButton.setOnClickListener { controller.dec() }
    }

    override fun onNewState(state: CounterState) {
        binding.counter.text = "${state.count}"
    }
}
