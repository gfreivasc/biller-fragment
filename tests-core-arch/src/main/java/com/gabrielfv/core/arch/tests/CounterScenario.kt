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
    viewProvider: ViewProvider<CounterController, CounterState> = counterViewProvider
) : Controller<CounterState>() {
    override val view: View<CounterState> = viewProvider.get(this)

    override fun initialize() = CounterState(0)

    fun inc() = setState { CounterState(it.count + 1) }

    fun dec() = setState { CounterState(it.count - 1) }
}

class CounterView(
    override val controller: CounterController
) : BindingView<CounterViewBinding, CounterState>(controller) {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binder: (CounterViewBinding) -> Unit
    ): android.view.View {
        val binding = CounterViewBinding.inflate(inflater, container, false)
        binder(binding)
        attachListeners()
        return binding.root
    }

    override fun onNewState(state: CounterState) {
        binding.counter.text = "${state.count}"
    }

    private fun attachListeners() {
        binding.incButton.setOnClickListener { controller.inc() }
        binding.decButton.setOnClickListener { controller.dec() }
    }
}

val counterViewProvider = ViewProvider { controller: CounterController -> CounterView(controller) }
