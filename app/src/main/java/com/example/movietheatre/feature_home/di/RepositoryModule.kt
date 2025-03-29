package com.example.movietheatre.feature_home.di

import com.example.movietheatre.feature_home.data.remote.repository.GenreRepositoryImpl
import com.example.movietheatre.feature_home.data.remote.repository.HomeRepositoryImpl
import com.example.movietheatre.feature_home.data.remote.service.GenresService
import com.example.movietheatre.feature_home.data.remote.service.HomeService
import com.example.movietheatre.feature_home.domain.repository.GenreRepository
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDiscoveryRepository(homeService: HomeService): HomeRepository {
        return HomeRepositoryImpl(homeService)
    }

    @Singleton
    @Provides
    fun provideGenreRepository(genresService: GenresService): GenreRepository {
        return GenreRepositoryImpl(genresService)
    }


}