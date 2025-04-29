package com.example.feature.movie_quiz.domain.model

data class QuizQuestion(
    val id: Int,
    val question: String,
    val imageUrl: String,
    val options: List<AnswerOption>,
    val correctOptionId: String
)