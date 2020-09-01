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

import com.gabrielfv.biller.home.domain.FetchBillsUseCase
import com.gabrielfv.biller.home.domain.interfaces.BillsSource
import com.gabrielfv.biller.home.mapper.BillMapper
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.InstantCoroutinesExecutor
import com.gabrielfv.core.nav.NavManager
import com.gabrielfv.core.nav.NavRegistry
import com.gabrielfv.core.nav.Routes
import com.gabrielfv.core.test.start
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class HomeControllerTest {
    private val mockView = mockk<View<HomeState>>(relaxed = true)
    private val mockNav = mockk<NavRegistry<Routes>>(relaxed = true)

    @Test
    fun initialStateIsLoading() {
        val subject = instantiate()

        val result = subject.initialState

        assertThat(result.loading, `is`(true))
        assertThat(result.bills.size, `is`(0))
    }

    @Test
    fun resumingAfterModifiedTriggersDataUpdate() {
        every { mockNav.readAny(eq(NavRegistry.BILLS_MODIFIED)) } returns true
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(Bill(1, "foo", "10", 0))
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
            listOf(Bill(1, "foo", "10", 0))
        )

        subject.onResume()

        verify(exactly = 0) { mockView.updateState(eq(expected)) }
    }

    @Test
    fun fetchesDataUponStart() {
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(Bill(1, "foo", "10", 0))
        )

        subject.start()

        verify { mockView.updateState(eq(expected)) }
    }

    private fun instantiate() = HomeController(
        fetchBillsUseCase = FetchBillsUseCase(FakeSource()),
        coroutinesExecutor = InstantCoroutinesExecutor(),
        mapper = BillMapper(),
        nav = mockNav,
        viewProvider = { mockView }
    )

    class FakeSource : BillsSource {
        override suspend fun get(): List<DomainBill> {
            return listOf(
                DomainBill(1, "foo", 1000)
            )
        }
    }
}
