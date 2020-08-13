package com.gabrielfv.biller.home

import com.gabrielfv.biller.home.domain.FetchBillsUseCase
import com.gabrielfv.biller.home.domain.interfaces.BillsSource
import com.gabrielfv.biller.home.mapper.BillMapper
import com.gabrielfv.biller.home.model.Bill
import com.gabrielfv.core.arch.View
import com.gabrielfv.core.arch.coroutines.InstantCoroutinesExecutor
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

private typealias DomainBill = com.gabrielfv.biller.home.domain.entities.Bill

class HomeControllerTest {
    private val mockView = mockk<View<HomeState>>(relaxed = true)

    @Test
    fun initialStateIsLoading() {
        val subject = instantiate()

        val result = subject.initialize()

        assertThat(result.loading, `is`(true))
        assertThat(result.bills.size, `is`(0))
    }

    @Test
    fun resumingTriggersDataUpdate() {
        val subject = instantiate()
        val expected = HomeState(
            false,
            listOf(Bill(1, "foo", "10", 0))
        )

        subject.onResume()

        verify(atLeast = 1) { mockView.updateState(eq(expected)) }
    }

    private fun instantiate() = HomeController(
        fetchBillsUseCase = FetchBillsUseCase(FakeSource()),
        coroutinesExecutor = InstantCoroutinesExecutor(),
        mapper = BillMapper(),
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