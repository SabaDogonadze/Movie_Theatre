package com.example.movietheatre.feature_payment.di

import com.example.movietheatre.feature_payment.data.cache.CardManagerImpl
import com.example.movietheatre.feature_payment.domain.manager.CardManager
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
    abstract fun bindCardManager(impl: CardManagerImpl): CardManager
}