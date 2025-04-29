package com.example.feature.movie_quiz.presentation.model

data class QuizCategoryPresenter(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val questionCount: Int,
    val rewardCoins: Int,
    val difficulty: String,
    val isCompleted: Boolean
)