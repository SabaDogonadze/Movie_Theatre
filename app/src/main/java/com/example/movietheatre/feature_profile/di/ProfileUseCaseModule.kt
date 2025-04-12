package com.example.movietheatre.feature_profile.di

import com.example.movietheatre.core.domain.use_case.DeleteUsersTicketUserCase
import com.example.movietheatre.core.domain.use_case.DeleteUsersTicketUserCaseImpl
import com.example.movietheatre.core.domain.use_case.GetUserTicketsByStatusUseCase
import com.example.movietheatre.core.domain.use_case.GetUserTicketsByStatusUseCaseImpl
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