package com.example.movietheatre.feature_movie_quiz.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizCompletedDto(
    val userUid: String,
    val quizId: Int,
    val score: Int
)
