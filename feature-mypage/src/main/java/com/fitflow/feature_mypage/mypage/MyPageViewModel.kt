package com.fitflow.feature_mypage.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitflow.core_common.TempSession
import com.fitflow.core_domain.model.ClassType
import com.fitflow.core_domain.model.ReservationStatus
import com.fitflow.core_domain.repository.ReservationRepository
import com.fitflow.core_domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    userRepository: UserRepository,
    reservationRepository: ReservationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState

    init {
        viewModelScope.launch {
            userRepository.observeCurrentUser()
                .combine(reservationRepository.observeMyReservations(TempSession.USER_ID)) { user, reservations ->
                    val zone = ZoneId.systemDefault()
                    val formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")
                    MyPageUiState(
                        nickname = user?.nickname ?: "사용자",
                        profileImageUrl = user?.profileImageUrl,
                        isLoading = false,
                        reservations = reservations.map { r ->
                            ReservationHistoryUiModel(
                                id = r.reservationId,
                                className = r.className,
                                classTypeText = if (r.classType == ClassType.YOGA) "요가" else "필라테스",
                                timeRangeText = "${formatter.format(r.timeStart.atZone(zone))} - ${formatter.format(r.timeEnd.atZone(zone))}",
                                location = r.location,
                                statusText = when (r.status) {
                                    ReservationStatus.CONFIRMED -> "예약 완료"
                                    ReservationStatus.CANCELLED -> "취소됨"
                                    ReservationStatus.WAITLIST -> "대기중"
                                }
                            )
                        }
                    )
                }
                .collect { state -> _uiState.update { state } }
        }
    }
}