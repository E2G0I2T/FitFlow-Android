package com.fitflow.feature_booking.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitflow.core_domain.model.ClassType
import com.fitflow.core_domain.repository.ClassRepository
import com.fitflow.core_domain.repository.ReservationRepository
import com.fitflow.core_domain.repository.ReservationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val TEMP_USER_ID = 1L // TODO: 로그인 세션 관리 붙이면 실제 사용자 ID로 교체

@HiltViewModel
class BookingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val classRepository: ClassRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val classScheduleId: Long = checkNotNull(savedStateHandle["classId"])

    private val _uiState = MutableStateFlow(BookingUiState())
    val uiState: StateFlow<BookingUiState> = _uiState

    init {
        viewModelScope.launch {
            val schedule = classRepository.getClassById(classScheduleId)
            if (schedule == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "수업 정보를 찾을 수 없습니다") }
                return@launch
            }
            val zone = ZoneId.systemDefault()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            _uiState.update {
                it.copy(
                    isLoading = false,
                    className = schedule.className,
                    classTypeText = if (schedule.classType == ClassType.YOGA) "요가" else "필라테스",
                    instructorName = schedule.instructorName,
                    timeRangeText = "${formatter.format(schedule.startTime.atZone(zone))} - ${formatter.format(schedule.endTime.atZone(zone))}",
                    location = schedule.location,
                    capacity = schedule.capacity,
                    reservedCount = schedule.reservedCount
                )
            }
        }
    }

    fun onReserveClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isReserving = true, errorMessage = null) }
            when (val result = reservationRepository.reserve(TEMP_USER_ID, classScheduleId)) {
                is ReservationResult.Success -> {
                    _uiState.update { it.copy(isReserving = false, isReservationSuccess = true) }
                }
                is ReservationResult.Failure -> {
                    _uiState.update { it.copy(isReserving = false, errorMessage = result.message) }
                }
            }
        }
    }
}