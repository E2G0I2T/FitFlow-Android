package com.fitflow.feature_mypage.mypage

data class ReservationHistoryUiModel(
    val id: Long,
    val className: String,
    val classTypeText: String,
    val timeRangeText: String,
    val location: String,
    val statusText: String
)

data class MyPageUiState(
    val nickname: String = "",
    val profileImageUrl: String? = null,
    val reservations: List<ReservationHistoryUiModel> = emptyList(),
    val isLoading: Boolean = true
)