package com.example.movietheatre.core.di

import com.example.movietheatre.core.data.remote.service.TicketService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideTicketRepository(retrofit: Retrofit): TicketService {
        return retrofit.create(TicketService::class.java)
    }
}