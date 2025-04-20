package com.example.movietheatre.feature_movie_quiz.data.remote.model

import com.example.movietheatre.feature_movie_quiz.domain.model.AnswerOption
import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionDto(
    val id: Int,
    val question: String,
    val imageUrl: String,
    val options: List<AnswerOptionDto>,
    val correctOptionId: String
)
