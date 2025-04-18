package com.example.movietheatre.feature_profile.di

import com.example.movietheatre.feature_profile.data.remote.repository.DeleteUsersTicketRepositoryImpl
import com.example.movietheatre.feature_profile.data.remote.repository.ProfileRepositoryImpl
import com.example.movietheatre.feature_profile.data.remote.repository.ProfileTicketByStatusRepositoryImpl
import com.example.movietheatre.feature_profile.domain.repository.DeleteUsersTicketRepository
import com.example.movietheatre.feature_profile.domain.repository.ProfileRepository
import com.example.movietheatre.feature_profile.domain.repository.ProfileTicketByStatusRepository
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
    abstract fun bindsProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

}

