package com.example.feature.shop.di

import com.example.feature.shop.data.repository.OrderRepositoryImpl
import com.example.feature.shop.data.repository.ProductRepositoryImpl
import com.example.feature.shop.domain.repository.OrderRepository
import com.example.feature.shop.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShopRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository

}