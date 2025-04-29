package com.example.feature.movie_detail.di

import com.example.feature.movie_detail.domain.use_case.GetMovieDetailUseCase
import com.example.feature.movie_detail.domain.use_case.GetMovieDetailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDetailUseCaseRepository {

    @Singleton
    @Binds
    abstract fun bindMovieDetailUseCase(impl: GetMovieDetailUseCaseImpl): GetMovieDetailUseCase

}