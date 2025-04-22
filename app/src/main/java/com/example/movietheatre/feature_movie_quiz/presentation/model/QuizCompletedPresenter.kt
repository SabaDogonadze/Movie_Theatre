package com.example.movietheatre.feature_movie_quiz.presentation.model

data class QuizCompletedPresenter(
    val userUid: String,
    val quizId: Int,
    val score: Int?
)