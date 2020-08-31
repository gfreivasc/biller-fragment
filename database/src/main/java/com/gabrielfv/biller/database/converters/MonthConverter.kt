package com.gabrielfv.biller.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.*

class MonthConverter {
    @TypeConverter
    fun toInt(value: Int?): Month? {
        return value?.let { Month.of(it) }
    }

    @TypeConverter
    fun fromInt(month: Month?): Int? {
        return month?.value
    }
}
