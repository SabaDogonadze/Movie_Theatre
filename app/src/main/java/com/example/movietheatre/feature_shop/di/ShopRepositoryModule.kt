package com.example.movietheatre.feature_shop.di

import com.example.movietheatre.feature_shop.data.repository.OrderRepositoryImpl
import com.example.movietheatre.feature_shop.data.repository.ProductRepositoryImpl
import com.example.movietheatre.feature_shop.domain.repository.OrderRepository
import com.example.movietheatre.feature_shop.domain.repository.ProductRepository
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