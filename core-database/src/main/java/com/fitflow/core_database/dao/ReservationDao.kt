package com.fitflow.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fitflow.core_database.entity.ReservationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Upsert
    suspend fun upsert(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE userId = :userId ORDER BY reservedAt DESC")
    fun observeByUser(userId: Long): Flow<List<ReservationEntity>>

    @Query("UPDATE reservations SET status = :status WHERE id = :reservationId")
    suspend fun updateStatus(reservationId: Long, status: String)
}