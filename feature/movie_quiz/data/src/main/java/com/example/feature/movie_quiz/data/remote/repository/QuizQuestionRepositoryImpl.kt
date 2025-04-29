package com.example.feature.movie_quiz.data.remote.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_quiz.data.remote.mapper.toDomain
import com.example.feature.movie_quiz.data.remote.service.QuizService
import com.example.feature.movie_quiz.domain.model.QuizQuestion
import com.example.feature.movie_quiz.domain.repository.QuizQuestionsRepository
import javax.inject.Inject

class QuizQuestionRepositoryImpl @Inject constructor(
    private val quizService: QuizService,
    private val apiHelper: ApiHelper,
) : QuizQuestionsRepository {

    override suspend fun getQuizzesQuestion(id: Int): Resource<List<QuizQuestion>, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = { quizService.getQuizQuestionsById(id) }
        ).mapData { dto ->
            dto.map { it.toDomain() }
        }
    }
}
