package com.fitflow.feature.classinfo.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitflow.core_domain.model.ClassSchedule
import com.fitflow.core_domain.model.ClassType
import com.fitflow.core_domain.repository.ClassRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val classRepository: ClassRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            runCatching { classRepository.refreshClasses() }
                .onFailure { _uiState.update { it.copy(errorMessage = "데이터를 불러오지 못했습니다") } }
        }

        viewModelScope.launch {
            classRepository.observeUpcomingClasses().collect { classes ->
                _uiState.update {
                    it.copy(isLoading = false, classes = classes.map { schedule -> schedule.toUiModel() })
                }
            }
        }
    }

    private fun ClassSchedule.toUiModel(): ClassUiModel {
        val zone = ZoneId.systemDefault()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return ClassUiModel(
            id = id,
            className = className,
            classType = if (classType == ClassType.YOGA) "요가" else "필라테스",
            instructorName = instructorName,
            timeRangeText = "${formatter.format(startTime.atZone(zone))} - ${formatter.format(endTime.atZone(zone))}",
            location = location,
            reservedCount = reservedCount,
            capacity = capacity
        )
    }
}