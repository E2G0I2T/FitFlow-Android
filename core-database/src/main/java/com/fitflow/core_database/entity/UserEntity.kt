package com.fitflow.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val createdAt: Instant
)