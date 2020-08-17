package com.gabrielfv.biller.addbill.mappers

import com.gabrielfv.biller.addbill.AddBillAction
import com.gabrielfv.biller.addbill.domain.entities.NewBill

class NewBillMapper {

    fun map(action: AddBillAction): NewBill {
        return NewBill(
            name = action.name,
            expiryDay = action.expiryDay.toIntOrNull() ?: 0,
            fixedValue = action.isFixedValue,
            value = action.fixedValue.toIntOrNull()
        )
    }
}