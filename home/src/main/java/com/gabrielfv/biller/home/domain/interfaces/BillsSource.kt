package com.gabrielfv.biller.home.domain.interfaces

import com.gabrielfv.biller.home.domain.entities.Bill

interface BillsSource {
    suspend fun get(): List<Bill>
}