package com.fitflow.core_database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "reservations",
    foreignKeys = [
        ForeignKey(
            entity = ClassScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["classScheduleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("classScheduleId"), Index("userId")]
)
data class ReservationEntity(
    @PrimaryKey val id: Long,
    val userId: Long,
    val classScheduleId: Long,
    val status: String,          // "CONFIRMED" | "CANCELLED" | "WAITLIST"
    val reservedAt: Instant
)