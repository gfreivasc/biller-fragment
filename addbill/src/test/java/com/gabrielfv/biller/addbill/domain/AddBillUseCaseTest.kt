package com.gabrielfv.biller.addbill.domain

import com.gabrielfv.biller.addbill.domain.entities.NewBill
import com.gabrielfv.biller.addbill.domain.interfaces.BillsSource
import com.gabrielfv.biller.database.entities.Bill
import com.gabrielfv.core.arch.coroutines.InstantCoroutinesExecutor
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

class AddBillUseCaseTest {
    private val source = mockk<BillsSource>(relaxed = true)
    private val executor = InstantCoroutinesExecutor()

    @Test
    fun savesValueWithFixedValue() {
        val subject = AddBillUseCase(source)
        val expected = Bill(name = "A", expiryDay = 5, fixedValue = true, valueInCents = 100)

        executor.execute {
            subject.execute(NewBill("A", 5, true, 100))
        }

        coVerify { source.insert(eq(expected)) }
    }

    @Test
    fun ignoresValueWithoutFixedValue() {
        val subject = AddBillUseCase(source)
        val expected = Bill(name = "A", expiryDay = 5, fixedValue = false, valueInCents = null)

        executor.execute {
            subject.execute(NewBill("A", 5, false, 100))
        }

        coVerify { source.insert(eq(expected)) }
    }
}