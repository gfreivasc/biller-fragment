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