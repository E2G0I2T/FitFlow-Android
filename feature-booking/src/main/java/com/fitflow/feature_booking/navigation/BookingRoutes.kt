package com.fitflow.feature_booking.navigation

object BookingRoutes {
    const val PATTERN = "booking/{classId}"

    fun booking(classId: Long) = "booking/$classId"
}