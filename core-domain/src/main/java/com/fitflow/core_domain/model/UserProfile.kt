package com.fitflow.core_domain.model

data class UserProfile(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?
)