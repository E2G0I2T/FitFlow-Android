package com.fitflow.core_data.repository

import com.fitflow.core_database.dao.UserDao
import com.fitflow.core_database.entity.UserEntity
import com.fitflow.core_domain.model.UserProfile
import com.fitflow.core_domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override fun observeCurrentUser(): Flow<UserProfile?> {
        return userDao.observeUser(com.fitflow.core_common.TempSession.USER_ID).map { entity ->
            entity?.let { UserProfile(id = it.id, nickname = it.nickname, profileImageUrl = it.profileImageUrl) }
        }
    }

    override suspend fun saveUser(id: Long, nickname: String, profileImageUrl: String?) {
        userDao.upsert(
            UserEntity(
                id = id,
                email = "", // 카카오 이메일 동의항목을 못 받는 상태라 비워둠
                nickname = nickname,
                profileImageUrl = profileImageUrl,
                createdAt = Instant.now()
            )
        )
    }
}