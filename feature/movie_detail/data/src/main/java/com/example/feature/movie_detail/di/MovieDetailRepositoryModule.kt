package com.example.feature.movie_detail.di

import com.example.feature.movie_detail.data.remote.repository.MovieDetailRepositoryImpl
import com.example.feature.movie_detail.domain.repository.MovieDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsMovieDetailRepository(impl: MovieDetailRepositoryImpl): MovieDetailRepository

}