package com.example.feature.movie_quiz.domain.usecase

import com.example.core.domain.util.Resource
import com.example.feature.movie_quiz.domain.model.QuizCompleted
import com.example.feature.movie_quiz.domain.repository.QuizCategoryRepository
import javax.inject.Inject

class MarkQuizCompletedUseCase @Inject constructor(
    private val repository: QuizCategoryRepository,
) {
    suspend operator fun invoke(userId: String, quizId: Int): List<QuizCompleted> {
        return when (val result = repository.markQuizCompleted(userId, quizId)) {
            is Resource.Success -> result.data
            is Resource.Error -> emptyList()
        }
    }
}