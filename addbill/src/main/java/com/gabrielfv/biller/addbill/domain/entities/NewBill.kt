package com.gabrielfv.biller.addbill.domain.entities

data class NewBill(
    val name: String,
    val expiryDay: Int,
    val fixedValue: Boolean,
    val value: Int? = null,
)