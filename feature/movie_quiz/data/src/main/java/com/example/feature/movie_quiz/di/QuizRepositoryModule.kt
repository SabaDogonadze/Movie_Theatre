package com.example.feature.movie_quiz.di

import com.example.feature.movie_quiz.data.remote.repository.QuizCategoryRepositoryImpl
import com.example.feature.movie_quiz.data.remote.repository.QuizCompletedRepositoryImpl
import com.example.feature.movie_quiz.data.remote.repository.QuizQuestionRepositoryImpl
import com.example.feature.movie_quiz.data.remote.repository.SubmitAnswerRepositoryImpl
import com.example.feature.movie_quiz.domain.repository.QuizCategoryRepository
import com.example.feature.movie_quiz.domain.repository.QuizCompletedRepository
import com.example.feature.movie_quiz.domain.repository.QuizQuestionsRepository
import com.example.feature.movie_quiz.domain.repository.SubmitAnswerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuizRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuizCategoryRepository(
        quizRepositoryImpl: QuizCategoryRepositoryImpl,
    ): QuizCategoryRepository

    @Binds
    @Singleton
    abstract fun bindSubmitAnswerRepository(
        submitAnswerRepositoryImpl: SubmitAnswerRepositoryImpl,
    ): SubmitAnswerRepository

    @Binds
    @Singleton
    abstract fun bindQuizCompletedRepository(
        quizCompletedRepositoryImpl: QuizCompletedRepositoryImpl,
    ): QuizCompletedRepository

    @Binds
    @Singleton
    abstract fun bindQuizQuestionRepository(
        quizQuestionRepositoryImpl: QuizQuestionRepositoryImpl,
    ): QuizQuestionsRepository
}