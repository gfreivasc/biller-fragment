package com.gabrielfv.biller.addbill.domain.entities

import com.gabrielfv.biller.addbill.domain.Either
import com.gabrielfv.biller.addbill.domain.entities.errors.ExpiryDayError
import com.gabrielfv.biller.addbill.domain.isLeft

data class Validations(
    val name: Either<Unit, Unit>,
    val expiryDay: Either<ExpiryDayError, Unit>,
    val fixedValue: Either<Unit, Unit>
) {

    val hasErrors get() =
        name.isLeft() || expiryDay.isLeft() || fixedValue.isLeft()
}