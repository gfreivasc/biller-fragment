package com.gabrielfv.biller.addbill

import androidx.navigation.fragment.findNavController
import com.gabrielfv.biller.addbill.domain.AddBillUseCase
import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.errors.ExpiryDayError
import com.gabrielfv.biller.addbill.domain.validators.ExpiryDayValidator
import com.gabrielfv.biller.addbill.domain.map
import com.gabrielfv.biller.addbill.mappers.NewBillMapper
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.CoroutinesExecutor
import com.gabrielfv.core.arch.coroutines.MainCoroutinesExecutor
import com.gabrielfv.core.arch.extras.ViewProvider

class AddBillController(
    private val expiryDayValidator: ExpiryDayValidator = ExpiryDayValidator(),
    private val addBillUseCase: AddBillUseCase = AddBillUseCase(),
    private val mapper: NewBillMapper = NewBillMapper(),
    private val coroutinesExecutor: CoroutinesExecutor = MainCoroutinesExecutor(),
    viewProvider: ViewProvider<AddBillController, AddBillState> = ViewProvider { AddBillView(it) }
) : Controller<AddBillState>() {
    override val view: View<AddBillState> = viewProvider.get(this)
    override val initialState: AddBillState
        get() = AddBillState(
            false,
            null,
            null,
            null,
        )

    fun addBill(action: AddBillAction) {
        val newBill = mapper.map(action)
        var errors = false

        expiryDayValidator.execute(newBill.expiryDay)
            .map({ error ->
                errors = true
                setState { it.copy(expiryDayError = mapExpiryDayError(error)) }
            })

        if (hasNameError(newBill)) errors = true
        if (hasFixedValueError(newBill)) errors = true

        if (!errors) {
            executeAddBill(newBill)
        }
    }

    fun setFixedValue(fixed: Boolean) {
        setState { it.copy(showValueField = fixed) }
    }

    private fun executeAddBill(newBill: NewBill) {
        coroutinesExecutor.execute {
            addBillUseCase.execute(newBill)
            findNavController().popBackStack()
        }
    }

    private fun hasNameError(newBill: NewBill): Boolean {
        if (newBill.name.isBlank()) {
            setState { it.copy(nameError = getString(R.string.field_blank_error)) }
            return true
        }
        return false
    }

    private fun hasFixedValueError(newBill: NewBill): Boolean {
        if (newBill.fixedValue && newBill.value == null) {
            setState { it.copy(fixedValueError = getString(R.string.field_blank_error)) }
            return true
        }
        return false
    }

    private fun mapExpiryDayError(error: ExpiryDayError) = when (error) {
        is ExpiryDayError.NotARecurringMonthDay -> getString(R.string.invalid_month_day)
        is ExpiryDayError.NotSafeForFebruary -> getString(R.string.you_nuts_man)
    }
}