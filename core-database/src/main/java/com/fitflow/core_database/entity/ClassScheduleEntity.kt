package com.fitflow.core_database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "class_schedules",
    foreignKeys = [
        ForeignKey(
            entity = InstructorEntity::class,
            parentColumns = ["id"],
            childColumns = ["instructorId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("instructorId")]
)
data class ClassScheduleEntity(
    @PrimaryKey val id: Long,
    val className: String,
    val classType: String,       // "YOGA" | "PILATES"
    val instructorId: Long,
    val startTime: Instant,
    val endTime: Instant,
    val capacity: Int,
    val reservedCount: Int,
    val location: String
)