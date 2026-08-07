package com.fitflow.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fitflow.core_database.entity.ClassScheduleEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

data class ClassScheduleWithInstructor(
    val id: Long,
    val className: String,
    val classType: String,
    val instructorName: String,
    val startTime: Instant,
    val endTime: Instant,
    val capacity: Int,
    val reservedCount: Int,
    val location: String
)

@Dao
interface ClassScheduleDao {
    @Upsert
    suspend fun upsertAll(schedules: List<ClassScheduleEntity>)

    @Query("SELECT * FROM class_schedules WHERE startTime >= :from ORDER BY startTime ASC")
    fun observeUpcoming(from: Instant): Flow<List<ClassScheduleEntity>>

    @Query("SELECT * FROM class_schedules WHERE id = :scheduleId")
    suspend fun getById(scheduleId: Long): ClassScheduleEntity?

    @Query("""
    SELECT cs.id, cs.className, cs.classType, i.name AS instructorName,
           cs.startTime, cs.endTime, cs.capacity, cs.reservedCount, cs.location
    FROM class_schedules cs
    INNER JOIN instructors i ON cs.instructorId = i.id
    WHERE cs.startTime >= :from
    ORDER BY cs.startTime ASC
""")
    fun observeUpcomingWithInstructor(from: Instant): Flow<List<ClassScheduleWithInstructor>>

    @Query("SELECT COUNT(*) FROM class_schedules")
    suspend fun count(): Int

    @Query("""
    SELECT cs.id, cs.className, cs.classType, i.name AS instructorName,
           cs.startTime, cs.endTime, cs.capacity, cs.reservedCount, cs.location
    FROM class_schedules cs
    INNER JOIN instructors i ON cs.instructorId = i.id
    WHERE cs.id = :scheduleId
""")
    suspend fun getByIdWithInstructor(scheduleId: Long): ClassScheduleWithInstructor?

    @Query("UPDATE class_schedules SET reservedCount = reservedCount + 1 WHERE id = :scheduleId")
    suspend fun incrementReservedCount(scheduleId: Long)

    @Query("SELECT COUNT(*) FROM class_schedules WHERE startTime >= :from")
    suspend fun countUpcoming(from: Instant): Int
}