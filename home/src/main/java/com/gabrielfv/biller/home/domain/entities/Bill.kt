package com.gabrielfv.biller.home.domain.entities

data class Bill(
    val id: Int,
    val name: String,
    val valueInCents: Int
)