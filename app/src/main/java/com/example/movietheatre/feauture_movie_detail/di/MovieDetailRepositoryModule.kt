package com.example.movietheatre.feauture_movie_detail.di

import com.example.movietheatre.feature_home.data.remote.repository.HomeRepositoryImpl
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import com.example.movietheatre.feauture_movie_detail.data.remote.repository.MovieDetailRepositoryImpl
import com.example.movietheatre.feauture_movie_detail.domain.repository.MovieDetailRepository
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