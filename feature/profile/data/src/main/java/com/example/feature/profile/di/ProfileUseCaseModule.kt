package com.example.feature.profile.di

import com.example.feature.profile.domain.use_case.DeleteUsersTicketUserCase
import com.example.feature.profile.domain.use_case.DeleteUsersTicketUserCaseImpl
import com.example.feature.profile.domain.use_case.GetUserTicketsByStatusUseCase
import com.example.feature.profile.domain.use_case.GetUserTicketsByStatusUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileUseCaseModule {

    @Singleton
    @Binds
    abstract fun bindGetTicketsByStatusUseCase(impl: GetUserTicketsByStatusUseCaseImpl): GetUserTicketsByStatusUseCase

    @Singleton
    @Binds
    abstract fun bindDeleteUsersTicketUseCase(impl: DeleteUsersTicketUserCaseImpl): DeleteUsersTicketUserCase

}