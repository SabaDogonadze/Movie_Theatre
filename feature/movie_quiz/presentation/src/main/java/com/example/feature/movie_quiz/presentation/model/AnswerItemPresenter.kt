package com.example.feature.movie_quiz.presentation.model

data class AnswerItemPresenter(
    val id: String,
    val letter: String,
    val text: String,
    var isSelected: Boolean = false,
    val isCorrect: Boolean = false
)