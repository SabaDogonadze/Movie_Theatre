package com.example.feature.home.di

import com.example.feauture.home.domain.usecase.GetGenreListUseCase
import com.example.feauture.home.domain.usecase.GetGenreListUseCaseImpl
import com.example.feauture.home.domain.usecase.GetMovieListUseCase
import com.example.feauture.home.domain.usecase.GetMovieListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetMovieUseCase(impl: GetMovieListUseCaseImpl): GetMovieListUseCase

    @Singleton
    @Binds
    abstract fun bindGetGenreUseCase(impl: GetGenreListUseCaseImpl): GetGenreListUseCase
}