package com.example.movietheatre.feature_movie_quiz.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted

interface QuizCompletedRepository {
    suspend fun getQuizCompleted(userId: String): Resource<List<QuizCompleted>, NetworkError>
    suspend fun completeQuiz(userId: String, quizId: Int): Resource<List<QuizCompleted>, NetworkError>
}