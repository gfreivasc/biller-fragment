package com.gabrielfv.biller.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class BillHistory(
    @Embedded val bill: Bill,
    @Relation(
        parentColumn = "id",
        entityColumn = "billId"
    )
    val paymentHistory: List<Payment>
)
