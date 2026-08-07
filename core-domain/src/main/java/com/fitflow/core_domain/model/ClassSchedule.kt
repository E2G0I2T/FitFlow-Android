package com.fitflow.core_domain.model

import java.time.Instant

data class ClassSchedule(
    val id: Long,
    val className: String,
    val classType: ClassType,
    val instructorName: String,
    val startTime: Instant,
    val endTime: Instant,
    val location: String,
    val capacity: Int,
    val reservedCount: Int
)

enum class ClassType {
    YOGA, PILATES
}