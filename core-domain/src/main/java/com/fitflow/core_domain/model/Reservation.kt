package com.fitflow.core_domain.model

import java.time.Instant

data class Reservation(
    val id: Long,
    val classScheduleId: Long,
    val status: ReservationStatus,
    val reservedAt: Instant
)

enum class ReservationStatus {
    CONFIRMED, CANCELLED, WAITLIST
}