package com.gabrielfv.biller.addbill.domain.validators

import com.gabrielfv.biller.addbill.domain.Either
import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.entities.Validations
import com.gabrielfv.biller.addbill.domain.left
import com.gabrielfv.biller.addbill.domain.right

class NewBillValidator(
    private val expiryDayValidator: ExpiryDayValidator = ExpiryDayValidator()
) {

    fun execute(newBill: NewBill): Validations {
        val name = if (newBill.name.isBlank()) left(Unit) else right(Unit)
        val expiryDay = expiryDayValidator.execute(newBill.expiryDay)
        val fixedValue = validateFixedValue(newBill)

        return Validations(name, expiryDay, fixedValue)
    }

    private fun validateFixedValue(newBill: NewBill): Either<Unit, Unit> {
        return if (newBill.isFixedValue && newBill.fixedValue == null) {
            left(Unit)
        } else {
            right(Unit)
        }
    }
}