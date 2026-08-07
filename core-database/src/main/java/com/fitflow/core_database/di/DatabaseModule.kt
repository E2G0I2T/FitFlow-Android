package com.fitflow.core_database.di

import android.content.Context
import androidx.room.Room
import com.fitflow.core_database.FitFlowDatabase
import com.fitflow.core_database.dao.ClassScheduleDao
import com.fitflow.core_database.dao.InstructorDao
import com.fitflow.core_database.dao.ReservationDao
import com.fitflow.core_database.dao.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFitFlowDatabase(@ApplicationContext context: Context): FitFlowDatabase =
        Room.databaseBuilder(context, FitFlowDatabase::class.java, "fitflow.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    fun provideUserDao(database: FitFlowDatabase): UserDao = database.userDao()

    @Provides
    fun provideInstructorDao(database: FitFlowDatabase): InstructorDao = database.instructorDao()

    @Provides
    fun provideClassScheduleDao(database: FitFlowDatabase): ClassScheduleDao = database.classScheduleDao()

    @Provides
    fun provideReservationDao(database: FitFlowDatabase): ReservationDao = database.reservationDao()
}