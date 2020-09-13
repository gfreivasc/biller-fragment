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
package com.gabrielfv.biller.home

import androidx.navigation.NavHostController
import com.gabrielfv.biller.home.domain.FetchBillsUseCase
import com.gabrielfv.biller.home.domain.entities.PaymentState
import com.gabrielfv.biller.home.mapper.BillMapper
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.biller.home.model.Payment
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.InstantCoroutinesExecutor
import com.gabrielfv.core.nav.NavRegistry
import com.gabrielfv.core.nav.Routes
import com.gabrielfv.core.test.start
import com.gabrielfv.core.test.stateBundle
import io.mockk.*
import kotlinx.datetime.toLocalDate
import org.junit.Test

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class HomeControllerTest {
    private val mockView = mockk<View<HomeState>>(relaxed = true)
    private val mockNav = mockk<NavRegistry<Routes>>(relaxed = true)
    private val mockRoutes = mockk<Routes> {
        every { addBill } returns 12
    }
    private val mockUseCase = mockk<FetchBillsUseCase> {
        coEvery { execute() } returns FakeUseCase().execute()
    }
    private val mockNavHostController = mockk<NavHostController>(relaxed = true)

    @Test
    fun initialStateIsLoading() {
        val subject = instantiate()
        val expected = HomeState(true, listOf())

        subject.start()

        verify(ordering = Ordering.ORDERED) {
            mockView.updateState(eq(expected))
            mockView.updateState(any())
        }
    }

    @Test
    fun resumingAfterModifiedTriggersDataUpdate() {
        every { mockNav.readAny(eq(NavRegistry.BILLS_MODIFIED)) } returns true
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(viewPaid(), viewExpired())
        )

        subject.onResume()

        verify(atLeast = 1) { mockView.updateState(eq(expected)) }
    }

    @Test
    fun resumingWithoutModificationDoesNothing() {
        every { mockNav.readAny(eq(NavRegistry.BILLS_MODIFIED)) } returns false
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(viewPaid(), viewExpired())
        )

        subject.onResume()

        verify(exactly = 0) { mockView.updateState(eq(expected)) }
    }

    @Test
    fun fetchesDataUponStart() {
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(viewPaid(), viewExpired())
        )

        subject.start()

        verify { mockView.updateState(eq(expected)) }
    }

    @Test
    fun restoresSavedStateWhenPresent() {
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(viewExpired(), viewPaid(), viewExpired())
        )
        val savedState = stateBundle(expected)

        subject.start(savedState)

        verify { mockView.updateState(eq(expected)) }
    }

    @Test
    fun addBillNavigatesToAppropriatePlace() {
        every { mockNav.routes } returns mockRoutes
        val subject = instantiate()
        subject.navController = mockNavHostController

        subject.addBill()

        verify { mockNavHostController.navigate(eq(12)) }
    }

    private fun instantiate() = HomeController(
        fetchBillsUseCase = mockUseCase,
        coroutinesExecutor = InstantCoroutinesExecutor(),
        mapper = BillMapper(),
        nav = mockNav,
        viewProvider = { mockView }
    )

    class FakeUseCase {
        fun execute(): List<DomainBill> {
            return listOf(
                DomainBill(1, "foo", payment(PaymentState.State.PAID), valueInCents = 1000),
                DomainBill(2, "bar", payment(PaymentState.State.EXPIRED))
            )
        }
    }

    private fun viewPaid() = Bill(
        id = 1,
        name = "foo",
        payment = Payment("10", 0),
        state = payment(PaymentState.State.PAID),
    )

    private fun viewExpired() = Bill(
        id = 2,
        name = "bar",
        payment = null,
        state = payment(PaymentState.State.EXPIRED),
    )

    companion object {
        fun payment(state: PaymentState.State): PaymentState {
            return PaymentState("1999-01-01".toLocalDate(), state)
        }
    }
}
