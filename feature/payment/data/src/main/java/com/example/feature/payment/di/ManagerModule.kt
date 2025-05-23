package com.example.feature.payment.di

import com.example.feature.payment.data.cache.CardManagerImpl
import com.example.feature.payment.domain.manager.CardManager
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