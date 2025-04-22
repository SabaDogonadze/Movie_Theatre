package com.example.movietheatre.feature_movie_quiz.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.data.remote.mapper.toDomain
import com.example.movietheatre.feature_movie_quiz.data.remote.service.QuizService
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted
import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizCompletedRepository
import javax.inject.Inject

class QuizCompletedRepositoryImpl @Inject constructor(
    private val quizService: QuizService,
    private val apiHelper: ApiHelper
) : QuizCompletedRepository {

    override suspend fun getQuizCompleted(userId: String): Resource<List<QuizCompleted>, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = { quizService.getQuizCompleted(userId) }
        ).mapData { dtoList ->
            dtoList.map { it.toDomain() }
        }
    }

    override suspend fun completeQuiz(
        userId: String,
        quizId: Int
    ): Resource<List<QuizCompleted>, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = { quizService.quizUpdate(userId, quizId) }
        ).mapData { dtoList ->
            dtoList.map { it.toDomain() }
        }
    }
}