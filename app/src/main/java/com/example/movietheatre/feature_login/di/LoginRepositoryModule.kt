package com.example.movietheatre.feature_login.di

import com.example.movietheatre.feature_login.data.repository.LoginRepositoryImpl
import com.example.movietheatre.feature_login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository
}