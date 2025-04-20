package com.example.movietheatre.feature_movie_quiz.data.remote.repository

import android.util.Log
import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.data.remote.mapper.toDomain
import com.example.movietheatre.feature_movie_quiz.data.remote.service.QuizService
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion
import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizQuestionsRepository
import javax.inject.Inject

class QuizQuestionRepositoryImpl @Inject constructor(
    private val quizService: QuizService,
    private val apiHelper: ApiHelper
) : QuizQuestionsRepository {

    override suspend fun getQuizzesQuestion(id: Int):Resource<List<QuizQuestion>, NetworkError> {
        Log.d("QuizRepository", "Fetching quiz questions for id: $id")
        return try {
            apiHelper.handleHttpRequest(
                apiCall = { quizService.getQuizQuestionsById(id) }
            ).mapData { dto ->
                Log.d("QuizRepository", "Response received: $dto")
                if (dto != null) {
                    val domain = dto.map { it.toDomain() }
                    Log.d("QuizRepository", "Mapped to domain: $domain")
                    domain
                } else {
                    Log.e("QuizRepository", "Received null DTO from API")
                    throw Exception("Received null response from server")
                }
            }
        } catch (e: Exception) {
            Log.e("QuizRepository", "Error fetching quiz questions", e)
            Resource.Error(NetworkError.UnknownError)        }
    }
}
