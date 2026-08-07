package com.fitflow.core_domain.repository

import com.fitflow.core_domain.model.ClassSchedule
import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    fun observeUpcomingClasses(): Flow<List<ClassSchedule>>
    suspend fun refreshClasses()
    suspend fun getClassById(id: Long): ClassSchedule?
}