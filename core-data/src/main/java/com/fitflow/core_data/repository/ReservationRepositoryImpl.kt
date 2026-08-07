package com.fitflow.core_data.repository

import androidx.room.withTransaction
import com.fitflow.core_database.FitFlowDatabase
import com.fitflow.core_database.dao.ClassScheduleDao
import com.fitflow.core_database.dao.ReservationDao
import com.fitflow.core_database.dao.ReservationWithClass
import com.fitflow.core_database.entity.ReservationEntity
import com.fitflow.core_domain.model.ClassType
import com.fitflow.core_domain.model.ReservationDetail
import com.fitflow.core_domain.model.ReservationStatus
import com.fitflow.core_domain.repository.ClassRepository
import com.fitflow.core_domain.repository.ReservationRepository
import com.fitflow.core_domain.repository.ReservationResult
import com.fitflow.core_domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val database: FitFlowDatabase,
    private val reservationDao: ReservationDao,
    private val classScheduleDao: ClassScheduleDao
) : ReservationRepository {

    override suspend fun reserve(userId: Long, classScheduleId: Long): ReservationResult {
        return try {
            database.withTransaction {
                val schedule = classScheduleDao.getById(classScheduleId)
                    ?: return@withTransaction ReservationResult.Failure("존재하지 않는 수업입니다")

                if (schedule.reservedCount >= schedule.capacity) {
                    return@withTransaction ReservationResult.Failure("정원이 마감되었습니다")
                }

                if (reservationDao.countActiveReservation(userId, classScheduleId) > 0) {
                    return@withTransaction ReservationResult.Failure("이미 예약한 수업입니다")
                }

                reservationDao.upsert(
                    ReservationEntity(
                        id = System.currentTimeMillis(), // TODO: 백엔드 연동 시 서버 발급 id로 교체
                        userId = userId,
                        classScheduleId = classScheduleId,
                        status = "CONFIRMED",
                        reservedAt = Instant.now()
                    )
                )
                classScheduleDao.incrementReservedCount(classScheduleId)

                ReservationResult.Success
            }
        } catch (e: Exception) {
            ReservationResult.Failure("예약 중 오류가 발생했습니다")
        }
    }

    // 기존 클래스 안에 추가
    override fun observeMyReservations(userId: Long): Flow<List<ReservationDetail>> {
        return reservationDao.observeReservationsWithClass(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    private fun ReservationWithClass.toDomain(): ReservationDetail = ReservationDetail(
        reservationId = reservationId,
        className = className,
        classType = ClassType.valueOf(classType),
        timeStart = startTime,
        timeEnd = endTime,
        location = location,
        status = ReservationStatus.valueOf(status)
    )
}