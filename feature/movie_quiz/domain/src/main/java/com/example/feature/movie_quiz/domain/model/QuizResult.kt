package com.example.feature.movie_quiz.domain.model

data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int
)