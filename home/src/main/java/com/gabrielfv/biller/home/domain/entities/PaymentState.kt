package com.gabrielfv.biller.home.domain.entities

enum class PaymentState {
    OPEN,
    TO_BE_EXPIRED,
    EXPIRED,
    PAID
}
