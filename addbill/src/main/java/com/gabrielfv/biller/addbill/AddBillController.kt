package com.gabrielfv.biller.addbill

import com.gabrielfv.core.arch.Controller
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.extras.ViewProvider

class AddBillController(
    viewProvider: ViewProvider<AddBillController, AddBillState> = ViewProvider { AddBillView(it) }
) : Controller<AddBillState>() {
    override val view: View<AddBillState> = viewProvider.get(this)
    override val initialState: AddBillState
        get() = AddBillState(false)
}