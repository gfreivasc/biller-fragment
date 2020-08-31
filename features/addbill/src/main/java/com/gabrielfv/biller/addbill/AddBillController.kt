package com.gabrielfv.biller.addbill

import androidx.navigation.fragment.findNavController
import com.gabrielfv.biller.addbill.domain.AddBillUseCase
import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.entities.errors.ExpiryDayError
import com.gabrielfv.biller.addbill.domain.validators.ExpiryDayValidator
import com.gabrielfv.biller.addbill.domain.map
import com.gabrielfv.biller.addbill.domain.validators.NewBillValidator
import com.gabrielfv.biller.addbill.mappers.NewBillMapper
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.CoroutinesExecutor
import com.gabrielfv.core.arch.coroutines.MainCoroutinesExecutor
import com.gabrielfv.core.arch.extras.ViewProvider
import kotlin.math.exp

class AddBillController(
    private val newBillValidator: NewBillValidator = NewBillValidator(),
    private val addBillUseCase: AddBillUseCase = AddBillUseCase(),
    private val mapper: NewBillMapper = NewBillMapper(),
    private val coroutinesExecutor: CoroutinesExecutor = MainCoroutinesExecutor(),
    viewProvider: ViewProvider<AddBillController, AddBillState> = ViewProvider { AddBillView(it) }
) : Controller<AddBillState>() {
    override val view: View<AddBillState> = viewProvider.get(this)

    override fun initialize(): AddBillState {
        return AddBillState(
            false,
            null,
            null,
            null,
        )
    }

    fun addBill(action: AddBillAction) {
        val newBill = mapper.map(action)
        val validations = newBillValidator.execute(newBill)

        validations.name.map({
            setState { it.copy(nameError = getString(R.string.field_blank_error)) }
        }, {
            setState { it.copy(nameError = null) }
        })
        validations.expiryDay.map({ error ->
            setState { it.copy(expiryDayError = mapExpiryDayError(error)) }
        }, {
            setState { it.copy(expiryDayError = null) }
        })
        validations.fixedValue.map({
            setState { it.copy(fixedValueError = getString(R.string.field_blank_error)) }
        }, {
            setState { it.copy(fixedValueError = null) }
        })

        if (!validations.hasErrors) {
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

    private fun mapExpiryDayError(error: ExpiryDayError) = when (error) {
        is ExpiryDayError.NotARecurringMonthDay -> getString(R.string.invalid_month_day)
        is ExpiryDayError.NotSafeForFebruary -> getString(R.string.you_nuts_man)
    }
}