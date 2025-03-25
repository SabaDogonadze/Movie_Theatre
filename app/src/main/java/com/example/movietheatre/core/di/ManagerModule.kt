package com.example.movietheatre.core.di

import com.example.movietheatre.core.data.manager.UserSessionManagerImpl
import com.example.movietheatre.core.domain.manager.UserSessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindUserSessionManager(impl: UserSessionManagerImpl): UserSessionManager
}