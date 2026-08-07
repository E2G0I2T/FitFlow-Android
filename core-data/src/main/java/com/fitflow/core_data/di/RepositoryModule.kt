package com.fitflow.core_data.di

import com.fitflow.core_data.repository.ClassRepositoryImpl
import com.fitflow.core_data.repository.ReservationRepositoryImpl
import com.fitflow.core_data.repository.UserRepositoryImpl
import com.fitflow.core_domain.repository.ClassRepository
import com.fitflow.core_domain.repository.ReservationRepository
import com.fitflow.core_domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindClassRepository(impl: ClassRepositoryImpl): ClassRepository

    @Binds
    abstract fun bindReservationRepository(impl: ReservationRepositoryImpl): ReservationRepository

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}