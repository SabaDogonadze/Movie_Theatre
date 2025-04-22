package com.example.movietheatre.feature_movie_quiz.domain.usecase

import android.util.Log
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion
import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizQuestionsRepository
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val quizQuestionsRepository: QuizQuestionsRepository
) {
    suspend operator fun invoke(categoryId: Int): Resource<List<QuizQuestion>, NetworkError> {
        val result = quizQuestionsRepository.getQuizzesQuestion(categoryId)
        when (result) {
            is Resource.Success -> {
                if (result.data.isEmpty() || result.data.any { it.options.isEmpty() }) {
                    return Resource.Error(NetworkError.UnknownError)
                }
            }
            is Resource.Error -> {
                Log.e("GetQuizQuestionsUseCase", "Error: ${result.error}")
            }
        }
        return result
    }
}