package com.example.feature.movie_quiz.presentation.event

sealed class QuizCategorySideEffect {
    data class NavigateToQuiz(val categoryId: Int, val coins: Int) : com.example.feature.movie_quiz.presentation.event.QuizCategorySideEffect()
    data class ShowError(val message: String) : com.example.feature.movie_quiz.presentation.event.QuizCategorySideEffect()
}