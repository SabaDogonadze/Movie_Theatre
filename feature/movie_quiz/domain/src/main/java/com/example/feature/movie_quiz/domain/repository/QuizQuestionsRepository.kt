package com.example.feature.movie_quiz.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_quiz.domain.model.QuizQuestion

interface QuizQuestionsRepository {
    suspend fun getQuizzesQuestion(id: Int): Resource<List<QuizQuestion>, NetworkError>
}