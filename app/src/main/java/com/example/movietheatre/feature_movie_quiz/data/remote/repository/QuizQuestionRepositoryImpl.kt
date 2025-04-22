package com.example.movietheatre.feature_movie_quiz.data.remote.repository

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
    private val apiHelper: ApiHelper,
) : QuizQuestionsRepository {

    override suspend fun getQuizzesQuestion(id: Int): Resource<List<QuizQuestion>, NetworkError> {
        return  apiHelper.handleHttpRequest(
            apiCall = { quizService.getQuizQuestionsById(id) }
        ).mapData { dto ->
            dto.map { it.toDomain() }
        }
    }
}
