package com.example.feature.movie_quiz.presentation.model

data class QuizQuestionPresenter(
    val id: Int,
    val question: String,
    val imageUrl: String,
    val options: List<com.example.feature.movie_quiz.presentation.model.AnswerOptionPresenter>,
    val correctOptionId: String
)