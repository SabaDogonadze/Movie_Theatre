package com.example.feature.home.di

import com.example.feature.home.data.remote.service.GenresService
import com.example.feature.home.data.remote.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun provideGenreService(retrofit: Retrofit): GenresService {
        return retrofit.create(GenresService::class.java)
    }
}