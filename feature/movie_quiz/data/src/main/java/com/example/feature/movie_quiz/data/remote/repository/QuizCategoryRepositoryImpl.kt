package com.example.feature.movie_quiz.data.remote.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_quiz.data.remote.mapper.toDomain
import com.example.feature.movie_quiz.data.remote.service.QuizService
import com.example.feature.movie_quiz.domain.model.QuizCategory
import com.example.feature.movie_quiz.domain.model.QuizCompleted
import com.example.feature.movie_quiz.domain.repository.QuizCategoryRepository
import javax.inject.Inject

class QuizCategoryRepositoryImpl @Inject constructor(
    private val quizService: QuizService,
    private val apiHelper: ApiHelper,
) : QuizCategoryRepository {
    override suspend fun getQuizCategories(): Resource<List<QuizCategory>, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = { quizService.getAllQuizzes() }
        ).mapData {
            it.map { it.toDomain() }
        }
    }

    override suspend fun getCompletedQuizzes(userId: String): Resource<List<QuizCompleted>, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = { quizService.getQuizCompleted(userId) }
        ).mapData {
            it.map { it.toDomain() }
        }
    }

    override suspend fun markQuizCompleted(
        userId: String,
        quizId: Int,
    ): Resource<List<QuizCompleted>, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = { quizService.quizUpdate(userId, quizId) }
        ).mapData {
            it.map { it.toDomain() }
        }
    }
}