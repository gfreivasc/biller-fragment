package com.gabrielfv.biller.addbill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.gabrielfv.biller.addbill.databinding.AddBillViewBinding
import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.core.arch.BindingView
import com.gabrielfv.core.arch.Controller

class AddBillView(
    override val controller: AddBillController
) : BindingView<AddBillViewBinding, AddBillState>(controller) {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): AddBillViewBinding {
        return AddBillViewBinding.inflate(inflater, container, false)
    }

    override fun onStart() = with(binding) {
        checkBoxFixedValue.setOnCheckedChangeListener { _, isChecked ->
            controller.setFixedValue(isChecked)
        }

        addBillButton.setOnClickListener {
            val isFixed = checkBoxFixedValue.isChecked
            controller.addBill(AddBillAction(
                name = inputName.text.toString(),
                expiryDay = inputExpiryDay.text.toString(),
                isFixedValue = isFixed,
                fixedValue = if (isFixed) inputFixedValue.text.toString() else null
            ))
        }
    }

    override fun onNewState(state: AddBillState) = with(binding) {
        valueGroup.isVisible = state.showValueField
        checkBoxFixedValue.isChecked = state.showValueField

        layoutInputName.error = state.nameError
        layoutInputExpiryDay.error = state.expiryDayError
        layoutInputFixedValue.error = state.fixedValueError
    }
}