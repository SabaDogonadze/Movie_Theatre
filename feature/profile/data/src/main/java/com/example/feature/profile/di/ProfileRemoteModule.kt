package com.example.feature.profile.di

import com.example.feature.profile.data.remote.service.DeleteUsersTicketService
import com.example.feature.profile.data.remote.service.ProfileTicketService
import com.example.feature.profile.data.remote.service.UserOrderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileRemoteModule {

    @Singleton
    @Provides
    fun provideProfileTicketService(retrofit: Retrofit): ProfileTicketService {
        return retrofit.create(ProfileTicketService::class.java)
    }


    @Singleton
    @Provides
    fun provideDeleteUsersTicketService(retrofit: Retrofit): DeleteUsersTicketService {
        return retrofit.create(DeleteUsersTicketService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserOrderService(retrofit: Retrofit): UserOrderService {
        return retrofit.create(UserOrderService::class.java)
    }
}
