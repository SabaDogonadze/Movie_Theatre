package com.example.movietheatre.feature_movie_quiz.domain.usecase

import com.example.movietheatre.feature_movie_quiz.domain.repository.SubmitAnswerRepository
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor(
    private val repository: SubmitAnswerRepository
) {
    suspend operator fun invoke(questionId: Int, selectedAnswerId: String): Boolean {
        return repository.submitAnswer(questionId, selectedAnswerId)
    }
}