package com.example.feature.movie_quiz.domain.usecase

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_quiz.domain.model.QuizCompleted
import com.example.feature.movie_quiz.domain.repository.QuizCategoryRepository
import javax.inject.Inject

class CompleteQuizUseCase @Inject constructor(
    private val repository: QuizCategoryRepository
) {
    suspend operator fun invoke(
        userId: String,
        quizId: Int
    ): Resource<List<QuizCompleted>, NetworkError> {
        return repository.markQuizCompleted(userId, quizId)
    }
}