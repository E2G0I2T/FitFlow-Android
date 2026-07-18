package com.fitflow.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fitflow.core_database.entity.ClassScheduleEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface ClassScheduleDao {
    @Upsert
    suspend fun upsertAll(schedules: List<ClassScheduleEntity>)

    @Query("SELECT * FROM class_schedules WHERE startTime >= :from ORDER BY startTime ASC")
    fun observeUpcoming(from: Instant): Flow<List<ClassScheduleEntity>>

    @Query("SELECT * FROM class_schedules WHERE id = :scheduleId")
    suspend fun getById(scheduleId: Long): ClassScheduleEntity?
}