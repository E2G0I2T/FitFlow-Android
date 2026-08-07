package com.fitflow.core_domain.model

import java.time.Instant

data class ReservationDetail(
    val reservationId: Long,
    val className: String,
    val classType: ClassType,
    val timeStart: Instant,
    val timeEnd: Instant,
    val location: String,
    val status: ReservationStatus
)