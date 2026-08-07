package com.fitflow.feature_booking.booking

data class BookingUiState(
    val className: String = "",
    val classTypeText: String = "",
    val instructorName: String = "",
    val timeRangeText: String = "",
    val location: String = "",
    val capacity: Int = 0,
    val reservedCount: Int = 0,
    val isLoading: Boolean = true,
    val isReserving: Boolean = false,
    val errorMessage: String? = null,
    val isReservationSuccess: Boolean = false
)