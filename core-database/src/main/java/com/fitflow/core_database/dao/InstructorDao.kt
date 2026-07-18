package com.fitflow.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fitflow.core_database.entity.InstructorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InstructorDao {
    @Upsert
    suspend fun upsertAll(instructors: List<InstructorEntity>)

    @Query("SELECT * FROM instructors ORDER BY name ASC")
    fun observeAll(): Flow<List<InstructorEntity>>

    @Query("SELECT * FROM instructors WHERE id = :instructorId")
    suspend fun getById(instructorId: Long): InstructorEntity?
}