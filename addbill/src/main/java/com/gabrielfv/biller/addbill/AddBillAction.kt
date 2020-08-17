package com.gabrielfv.biller.addbill

data class AddBillAction(
    val name: String,
    val expiryDay: String,
    val isFixedValue: Boolean,
    val fixedValue: String,
)
