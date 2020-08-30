package com.gabrielfv.biller.addbill

import android.view.View
import androidx.core.view.isVisible
import com.gabrielfv.biller.addbill.databinding.AddBillViewBinding
import com.gabrielfv.core.arch.BindingView

class AddBillView(
    override val controller: AddBillController
) : BindingView<AddBillViewBinding, AddBillState>(controller, R.layout.add_bill_view) {

    override fun bind(view: View): AddBillViewBinding {
        return AddBillViewBinding.bind(view)
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