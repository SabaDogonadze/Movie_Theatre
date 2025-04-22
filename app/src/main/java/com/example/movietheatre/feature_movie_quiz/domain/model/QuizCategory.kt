package com.example.movietheatre.feature_movie_quiz.domain.model

data class QuizCategory(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val questionCount: Int,
    val rewardCoins: Int,
    val difficulty: String,
    val isCompleted: Boolean = false
)