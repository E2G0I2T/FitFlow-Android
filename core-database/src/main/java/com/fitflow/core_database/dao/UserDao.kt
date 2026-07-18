package com.fitflow.core_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fitflow.core_database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsert(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun observeUser(userId: Long): Flow<UserEntity?>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun delete(userId: Long)
}