package com.gabrielfv.biller.addbill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.gabrielfv.biller.addbill.databinding.AddBillViewBinding
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.Controller

class AddBillView(
    override val controller: AddBillController
) : BindingView<AddBillViewBinding, AddBillState>(controller) {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): AddBillViewBinding {
        return AddBillViewBinding.inflate(inflater, container, false)
    }

    override fun onNewState(state: AddBillState) = with(binding) {
        valueGroup.isVisible = state.showValueField
        checkBoxFixedValue.isChecked = state.showValueField
    }
}