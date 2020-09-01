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