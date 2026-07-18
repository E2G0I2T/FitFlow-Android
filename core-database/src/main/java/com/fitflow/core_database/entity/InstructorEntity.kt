package com.fitflow.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "instructors")
data class InstructorEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val bio: String?,
    val profileImageUrl: String?,
    val specialty: String,       // "YOGA" | "PILATES" | "BOTH"
    val careerYears: Int?,
    val createdAt: Instant
)