package com.fitflow.core_database.converter

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverters {
    @TypeConverter
    fun fromEpochMilli(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)

    @TypeConverter
    fun toEpochMilli(instant: Instant?): Long? = instant?.toEpochMilli()
}