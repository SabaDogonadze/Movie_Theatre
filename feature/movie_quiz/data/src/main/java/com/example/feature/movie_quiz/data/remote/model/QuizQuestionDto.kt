package com.example.feature.movie_quiz.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestionDto(
    val id: Int,
    val question: String,
    val imageUrl: String,
    val options: List<AnswerOptionDto>,
    val correctOptionId: String
)
