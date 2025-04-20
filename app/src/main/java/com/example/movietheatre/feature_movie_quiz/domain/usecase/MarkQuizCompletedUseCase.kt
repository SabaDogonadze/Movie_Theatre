package com.example.movietheatre.feature_movie_quiz.domain.usecase

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted
import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizCategoryRepository
import javax.inject.Inject

class MarkQuizCompletedUseCase @Inject constructor(
    private val repository: QuizCategoryRepository
) {
    suspend operator fun invoke(userId: String, quizId: Int): List<QuizCompleted> {
        val result = repository.markQuizCompleted(userId, quizId)
        return when (result) {
            is Resource.Success -> result.data
            is Resource.Error -> emptyList()
        }
    }
}