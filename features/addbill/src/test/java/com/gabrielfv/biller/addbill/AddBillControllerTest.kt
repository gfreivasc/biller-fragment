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

import com.gabrielfv.biller.addbill.domain.AddBillUseCase
import com.gabrielfv.biller.addbill.domain.validators.NewBillValidator
import com.gabrielfv.biller.addbill.mappers.NewBillMapper
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.InstantCoroutinesExecutor
import com.gabrielfv.core.test.resources.spyingRes
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class AddBillControllerTest {
    private val validator = NewBillValidator()
    private val useCase = mockk<AddBillUseCase>()
    private val executor = InstantCoroutinesExecutor()
    private val mapper = NewBillMapper()
    private val mockView = mockk<View<AddBillState>>(relaxed = true)

    @Test
    fun validateFebruaryUnsafeDayReturnsCorrectMessage() {
        val subject = spyingRes(instantiate()) {
            R.string.you_nuts_man toString "Feb Error"
        }
        val expected = mockState(expiryDayError = "Feb Error")

        subject.addBill(AddBillAction("Lala", "29", false))

        verify {
            mockView.updateState(eq(expected))
        }
    }

    @Test
    fun validateNotRecurringMonthDayReturnsCorrectMessage() {
        val subject = spyingRes(instantiate()) {
            R.string.invalid_month_day toString "Month Error"
        }
        val expected = mockState(expiryDayError = "Month Error")

        subject.addBill(AddBillAction("Lala", "31", false))

        verify {
            mockView.updateState(eq(expected))
        }
    }

    @Test
    fun validateEmptyNameReturnsCorrectMessage() {
        val subject = spyingRes(instantiate()) {
            R.string.field_blank_error toString  "Empty"
        }
        val expected = mockState(nameError = "Empty")

        subject.addBill(AddBillAction("", "5", isFixedValue = false))

        verify {
            mockView.updateState(eq(expected))
        }
    }

    @Test
    fun validateEmptyFixedValueIfIsFixedReturnsCorrectMessage() {
        val subject = spyingRes(instantiate()) {
            R.string.field_blank_error toString "Empty"
        }
        val expected = mockState(fixedValueError = "Empty")

        subject.addBill(AddBillAction("Lala", "5", true))

        verify {
            mockView.updateState(eq(expected))
        }
    }

    private fun instantiate() = AddBillController(
        newBillValidator = validator,
        addBillUseCase = useCase,
        mapper = mapper,
        coroutinesExecutor = executor,
        viewProvider = { mockView }
    )

    private fun mockState(
        showValueField: Boolean = false,
        expiryDayError: String? = null,
        nameError: String? = null,
        fixedValueError: String? = null,
    ) = AddBillState(showValueField, expiryDayError, nameError, fixedValueError)
}