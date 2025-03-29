package com.example.movietheatre.feature_home.di

import com.example.movietheatre.feature_home.domain.repository.GenreRepository
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import com.example.movietheatre.feature_home.domain.usecase.GetGenreListUseCase
import com.example.movietheatre.feature_home.domain.usecase.GetGenreListUseCaseImpl
import com.example.movietheatre.feature_home.domain.usecase.GetMovieListUseCase
import com.example.movietheatre.feature_home.domain.usecase.GetMovieListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetMovieUseCase(homeRepository: HomeRepository): GetMovieListUseCase {
        return GetMovieListUseCaseImpl(homeRepository)
    }

    @Singleton
    @Provides
    fun provideGetGenreUseCase(genreRepository: GenreRepository): GetGenreListUseCase {
        return GetGenreListUseCaseImpl(genreRepository)
    }

}