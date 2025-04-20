package com.example.movietheatre.feature_movie_quiz.presentation.model

data class QuizQuestionPresenter(
    val id: Int,
    val question: String,
    val imageUrl: String,
    val options: List<AnswerOptionPresenter>,
    val correctOptionId: String
)