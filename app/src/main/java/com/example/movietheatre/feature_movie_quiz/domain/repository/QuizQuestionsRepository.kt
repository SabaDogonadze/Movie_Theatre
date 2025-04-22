package com.example.movietheatre.feature_movie_quiz.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion

interface QuizQuestionsRepository {
    suspend fun getQuizzesQuestion(id:Int): Resource<List<QuizQuestion>, NetworkError>
}