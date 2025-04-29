package com.example.feature.movie_quiz.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_quiz.domain.model.QuizCompleted

interface QuizCompletedRepository {
    suspend fun getQuizCompleted(userId: String): Resource<List<QuizCompleted>, NetworkError>
    suspend fun completeQuiz(
        userId: String,
        quizId: Int,
    ): Resource<List<QuizCompleted>, NetworkError>
}