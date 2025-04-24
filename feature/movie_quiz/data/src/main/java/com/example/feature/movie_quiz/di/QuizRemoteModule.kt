package com.example.feature.movie_quiz.di

import com.example.feature.movie_quiz.data.remote.service.QuizService
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