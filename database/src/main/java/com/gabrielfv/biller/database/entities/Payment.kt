package com.gabrielfv.biller.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Month

@Entity
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val billId: Long,
    val year: Int,
    val month: Month,
    val valueInCents: Int,
)
