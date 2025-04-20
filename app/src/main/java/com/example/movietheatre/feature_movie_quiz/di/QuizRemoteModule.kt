package com.example.movietheatre.feature_movie_quiz.di

import com.example.movietheatre.feature_movie_quiz.data.remote.service.QuizService
import com.example.movietheatre.feature_profile.data.remote.service.ProfileTicketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizRemoteModule {

    @Singleton
    @Provides
    fun provideQuizService(retrofit: Retrofit): QuizService {
        return retrofit.create(QuizService::class.java)
    }
}