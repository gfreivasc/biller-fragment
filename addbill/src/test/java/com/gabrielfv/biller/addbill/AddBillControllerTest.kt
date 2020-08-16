package com.gabrielfv.biller.addbill

import com.gabrielfv.biller.addbill.domain.ExpiryDayValidator
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.test.resources.spyingRes
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class AddBillControllerTest {
    private val mockView = mockk<View<AddBillState>>(relaxed = true)
    private val expiryDay = ExpiryDayValidator()

    @Test
    fun validateFebruaryUnsafeDayReturnsCorrectMessage() {
        val subject = spyingRes(instantiate()) {
            R.string.you_nuts_man toString "Feb Error"
        }
        val expected = AddBillState(false, "Feb Error")

        subject.addBill(AddBillAction(29))

        verify {
            mockView.updateState(eq(expected))
        }
    }

    @Test
    fun validateNotRecurringMonthDayReturnsCorrectMessage() {
        val subject = spyingRes(instantiate()) {
            R.string.invalid_month_day toString "Month Error"
        }
        val expected = AddBillState(false, "Month Error")

        subject.addBill(AddBillAction(31))

        verify {
            mockView.updateState(eq(expected))
        }
    }

    private fun instantiate() = AddBillController(expiryDay) { mockView }
}