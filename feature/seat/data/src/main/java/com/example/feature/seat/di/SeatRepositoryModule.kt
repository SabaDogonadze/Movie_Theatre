package com.example.feature.seat.di

import com.example.feature.seat.data.repository.SeatRepositoryImpl
import com.example.feature.seat.domain.repository.SeatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SeatRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideSeatRepository(impl: SeatRepositoryImpl): SeatRepository
}