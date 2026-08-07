package com.fitflow.core_data.repository

import com.fitflow.core_database.dao.ClassScheduleDao
import com.fitflow.core_database.dao.ClassScheduleWithInstructor
import com.fitflow.core_database.dao.InstructorDao
import com.fitflow.core_database.entity.ClassScheduleEntity
import com.fitflow.core_database.entity.InstructorEntity
import com.fitflow.core_domain.model.ClassSchedule
import com.fitflow.core_domain.model.ClassType
import com.fitflow.core_domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val classScheduleDao: ClassScheduleDao,
    private val instructorDao: InstructorDao
) : ClassRepository {

    override fun observeUpcomingClasses(): Flow<List<ClassSchedule>> {
        return classScheduleDao.observeUpcomingWithInstructor(Instant.now())
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun refreshClasses() {
        // TODO: 백엔드 API 준비되면 여기서 core-network의 ApiService 호출 → upsert로 교체
        if (classScheduleDao.countUpcoming(Instant.now()) == 0) {
            seedMockData()
        }
    }

    override suspend fun getClassById(id: Long): ClassSchedule? {
        return classScheduleDao.getByIdWithInstructor(id)?.toDomain()
    }

    private suspend fun seedMockData() {
        val now = Instant.now()

        instructorDao.upsertAll(
            listOf(
                InstructorEntity(1, "김지은", null, null, "YOGA", null, now),
                InstructorEntity(2, "박서준", null, null, "PILATES", null, now),
                InstructorEntity(3, "이하늘", null, null, "YOGA", null, now)
            )
        )

        classScheduleDao.upsertAll(
            listOf(
                ClassScheduleEntity(1, "모닝 하타요가", "YOGA", 1, now.plusSeconds(3600), now.plusSeconds(7200), 12, 8, "1층 스튜디오 A"),
                ClassScheduleEntity(2, "코어 필라테스", "PILATES", 2, now.plusSeconds(14400), now.plusSeconds(18000), 12, 12, "2층 스튜디오 B"),
                ClassScheduleEntity(3, "빈야사 플로우", "YOGA", 3, now.plusSeconds(43200), now.plusSeconds(46800), 15, 5, "1층 스튜디오 A"),
                ClassScheduleEntity(4, "저녁 필라테스", "PILATES", 2, now.plusSeconds(46800), now.plusSeconds(50400), 10, 3, "2층 스튜디오 B")
            )
        )
    }

    private fun ClassScheduleWithInstructor.toDomain(): ClassSchedule = ClassSchedule(
        id = id,
        className = className,
        classType = ClassType.valueOf(classType),
        instructorName = instructorName,
        startTime = startTime,
        endTime = endTime,
        location = location,
        capacity = capacity,
        reservedCount = reservedCount
    )
}