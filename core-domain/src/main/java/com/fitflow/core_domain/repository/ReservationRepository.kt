package com.fitflow.core_domain.repository

import com.fitflow.core_domain.model.ReservationDetail
import kotlinx.coroutines.flow.Flow

sealed interface ReservationResult {
    data object Success : ReservationResult
    data class Failure(val message: String) : ReservationResult
}

interface ReservationRepository {
    suspend fun reserve(userId: Long, classScheduleId: Long): ReservationResult
    fun observeMyReservations(userId: Long): Flow<List<ReservationDetail>>
}