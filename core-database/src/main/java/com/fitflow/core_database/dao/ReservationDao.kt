package com.fitflow.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fitflow.core_database.entity.ReservationEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface ReservationDao {
    @Upsert
    suspend fun upsert(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE userId = :userId ORDER BY reservedAt DESC")
    fun observeByUser(userId: Long): Flow<List<ReservationEntity>>

    @Query("UPDATE reservations SET status = :status WHERE id = :reservationId")
    suspend fun updateStatus(reservationId: Long, status: String)

    @Query("SELECT COUNT(*) FROM reservations WHERE userId = :userId AND classScheduleId = :classScheduleId AND status = 'CONFIRMED'")
    suspend fun countActiveReservation(userId: Long, classScheduleId: Long): Int

    @Query("""
        SELECT r.id AS reservationId, cs.className, cs.classType,
               cs.startTime, cs.endTime, cs.location, r.status
        FROM reservations r
        INNER JOIN class_schedules cs ON r.classScheduleId = cs.id
        WHERE r.userId = :userId
        ORDER BY cs.startTime DESC
    """)
    fun observeReservationsWithClass(userId: Long): Flow<List<ReservationWithClass>>
}

data class ReservationWithClass(
    val reservationId: Long,
    val className: String,
    val classType: String,
    val startTime: Instant,
    val endTime: Instant,
    val location: String,
    val status: String
)