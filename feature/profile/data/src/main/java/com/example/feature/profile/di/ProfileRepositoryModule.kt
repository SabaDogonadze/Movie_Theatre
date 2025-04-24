package com.example.feature.profile.di

import com.example.feature.profile.data.remote.repository.DeleteUsersTicketRepositoryImpl
import com.example.feature.profile.data.remote.repository.ProfileTicketByStatusRepositoryImpl
import com.example.feature.profile.data.remote.repository.UserOrderRepositoryImpl
import com.example.feature.profile.domain.repository.DeleteUsersTicketRepository
import com.example.feature.profile.domain.repository.ProfileTicketByStatusRepository
import com.example.feature.profile.domain.repository.UserOrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsProfileTicketByStatusRepository(impl: ProfileTicketByStatusRepositoryImpl): ProfileTicketByStatusRepository


    @Singleton
    @Binds
    abstract fun bindsDeleteUsersTicketRepository(impl: DeleteUsersTicketRepositoryImpl): DeleteUsersTicketRepository

    @Singleton
    @Binds
    abstract fun bindsUserOrderRepository(impl: UserOrderRepositoryImpl): UserOrderRepository


}

