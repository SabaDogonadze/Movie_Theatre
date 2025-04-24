package com.example.feature.movie_quiz.domain.model

data class QuizCompleted(
    val userUid: String,
    val quizId: Int,
    val score: Int?
)
