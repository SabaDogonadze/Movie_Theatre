package com.example.movietheatre.feature_movie_quiz.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted

interface QuizCategoryRepository {
    suspend fun getQuizCategories(): Resource<List<QuizCategory>, NetworkError>
    suspend fun getCompletedQuizzes(userId: String): Resource<List<QuizCompleted>, NetworkError>
    suspend fun markQuizCompleted(userId: String, quizId: Int): Resource<List<QuizCompleted>, NetworkError>
}
