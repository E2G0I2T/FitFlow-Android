package com.fitflow.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fitflow.core_database.converter.InstantConverters
import com.fitflow.core_database.dao.ClassScheduleDao
import com.fitflow.core_database.dao.InstructorDao
import com.fitflow.core_database.dao.ReservationDao
import com.fitflow.core_database.dao.UserDao
import com.fitflow.core_database.entity.ClassScheduleEntity
import com.fitflow.core_database.entity.InstructorEntity
import com.fitflow.core_database.entity.ReservationEntity
import com.fitflow.core_database.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        InstructorEntity::class,
        ClassScheduleEntity::class,
        ReservationEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InstantConverters::class)
abstract class FitFlowDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun instructorDao(): InstructorDao
    abstract fun classScheduleDao(): ClassScheduleDao
    abstract fun reservationDao(): ReservationDao
}