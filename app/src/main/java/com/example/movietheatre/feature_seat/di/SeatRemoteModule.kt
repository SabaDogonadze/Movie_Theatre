package com.example.movietheatre.feature_seat.di

import com.example.movietheatre.feature_seat.data.remote.service.SeatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SeatRemoteModule {

    @Provides
    @Singleton
    fun provideSeatService(retrofit: Retrofit): SeatService {
        return retrofit.create(SeatService::class.java)
    }
}