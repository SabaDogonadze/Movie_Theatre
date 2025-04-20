/*
package com.example.movietheatre.feature_movie_quiz.data.remote.repository

import com.example.movietheatre.feature_movie_quiz.data.MockQuizDataProvider
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizResult
import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val mockQuizDataProvider: MockQuizDataProvider
) : QuizRepository {

    private val answeredQuestions = mutableMapOf<Int, String>()
    private var score = 0

    override suspend fun getQuizQuestions(categoryId: Int?): List<QuizQuestion> {
        return if (categoryId != null) {
            mockQuizDataProvider.getQuestionsForCategory(categoryId)
        } else {
            mockQuizDataProvider.provideQuizQuestions()
        }
    }

    override suspend fun submitAnswer(questionId: Int, selectedAnswerId: String): Boolean {
        val question = mockQuizDataProvider.provideQuizQuestions().find { it.id == questionId }
        val isCorrect = question?.correctOptionId == selectedAnswerId

        answeredQuestions[questionId] = selectedAnswerId

        if (isCorrect) {
            score += 10
        }

        return isCorrect
    }

    override suspend fun getQuizResult(): QuizResult {
        return QuizResult(
            totalQuestions = answeredQuestions.size,
            correctAnswers = score / 10,
            score = score
        )
    }

    override suspend fun getQuizCategories(): List<QuizCategory> {
        return mockQuizDataProvider.provideQuizCategories()
    }
}*/
