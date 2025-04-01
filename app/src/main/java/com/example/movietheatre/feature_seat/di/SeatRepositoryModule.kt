package com.example.movietheatre.feature_seat.di

import com.example.movietheatre.feature_seat.data.repository.SeatRepositoryImpl
import com.example.movietheatre.feature_seat.domain.repository.SeatRepository
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