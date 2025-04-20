package com.example.movietheatre.feature_movie_quiz.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizCategoryDto(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val questionCount: Int,
    val rewardCoins: Int,
    val difficulty: String
)
