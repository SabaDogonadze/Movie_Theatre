package com.example.movietheatre.feature_movie_quiz.presentation.event

sealed class QuizCategorySideEffect {
    data class NavigateToQuiz(val categoryId: Int, val coins: Int) : QuizCategorySideEffect()
    data class ShowError(val message: String) : QuizCategorySideEffect()
}