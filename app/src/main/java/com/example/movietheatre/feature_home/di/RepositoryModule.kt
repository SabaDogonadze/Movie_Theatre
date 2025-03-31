package com.example.movietheatre.feature_home.di

import com.example.movietheatre.feature_home.data.remote.repository.GenreRepositoryImpl
import com.example.movietheatre.feature_home.data.remote.repository.HomeRepositoryImpl
import com.example.movietheatre.feature_home.domain.repository.GenreRepository
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsDiscoveryRepository(impl: HomeRepositoryImpl): HomeRepository

    @Singleton
    @Binds
    abstract fun bindsGenreRepository(impl: GenreRepositoryImpl): GenreRepository


}