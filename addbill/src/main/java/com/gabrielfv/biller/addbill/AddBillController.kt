package com.gabrielfv.biller.addbill

import com.gabrielfv.biller.addbill.domain.errors.ExpiryDayError
import com.gabrielfv.biller.addbill.domain.ExpiryDayValidator
import com.gabrielfv.biller.addbill.domain.map
import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider

class AddBillController(
    private val expiryDayValidator: ExpiryDayValidator = ExpiryDayValidator(),
    viewProvider: ViewProvider<AddBillController, AddBillState> = ViewProvider { AddBillView(it) }
) : Controller<AddBillState>() {
    override val view: View<AddBillState> = viewProvider.get(this)
    override val initialState: AddBillState
        get() = AddBillState(false, null)

    fun addBill(action: AddBillAction) {
        expiryDayValidator.execute(action.expiryDay)
            .map({ error ->
                setState { AddBillState(it.showValueField, mapExpiryDayError(error)) }
            })
    }

    private fun mapExpiryDayError(error: ExpiryDayError) = when (error) {
        is ExpiryDayError.NotARecurringMonthDay -> getString(R.string.invalid_month_day)
        is ExpiryDayError.NotSafeForFebruary -> getString(R.string.you_nuts_man)
    }
}