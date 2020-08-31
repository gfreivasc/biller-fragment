package com.gabrielfv.biller.home.domain.entities

data class Bill(
    val id: Long,
    val name: String,
    val paymentState: PaymentState,
    val fixedValue: Boolean,
    val valueInCents: Int?,
)
