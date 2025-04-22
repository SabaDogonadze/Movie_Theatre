package com.example.movietheatre.feature_movie_quiz.domain.usecase

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted
import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizCategoryRepository
import javax.inject.Inject

class GetQuizCompletedUseCase @Inject constructor(
    private val repository: QuizCategoryRepository
) {
    suspend operator fun invoke(userId: String): Resource<List<QuizCompleted>, NetworkError> {
        return repository.getCompletedQuizzes(userId)
    }
}
