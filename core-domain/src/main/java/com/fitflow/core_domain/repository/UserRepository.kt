package com.fitflow.core_domain.repository

import com.fitflow.core_domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeCurrentUser(): Flow<UserProfile?>
    suspend fun saveUser(id: Long, nickname: String, profileImageUrl: String?)
}