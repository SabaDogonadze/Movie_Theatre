package com.example.feature.movie_quiz.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AnswerOptionDto(
    val id: String,
    val letter: String,
    val text: String
)
