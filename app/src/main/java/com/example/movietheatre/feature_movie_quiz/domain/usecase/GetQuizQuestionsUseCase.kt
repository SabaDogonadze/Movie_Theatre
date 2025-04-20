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
        Log.d("GetQuizQuestionsUseCase", "Invoking with categoryId: $categoryId")
        val result = quizQuestionsRepository.getQuizzesQuestion(categoryId)

        when (result) {
            is Resource.Success -> {
                Log.d("GetQuizQuestionsUseCase", "Success: ${result.data}")
                // Validate response - ensure we have questions with options
                if (result.data.isEmpty() || result.data.any { it.options.isEmpty() }) {
                    Log.e("GetQuizQuestionsUseCase", "Empty response or questions without options")
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