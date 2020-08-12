package com.gabrielfv.biller.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bill(
    @PrimaryKey(autoGenerate = true) val uuid: Int = 0,
    val name: String,
    val valueTimesTen: Int,
    val dueDay: Int
)