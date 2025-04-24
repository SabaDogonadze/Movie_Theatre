package com.example.feature.movie_quiz.presentation.event

sealed class QuizCategoryEvent {
    data object LoadCategories : com.example.feature.movie_quiz.presentation.event.QuizCategoryEvent()
    data class SelectCategory(val categoryId: Int) : com.example.feature.movie_quiz.presentation.event.QuizCategoryEvent()
    data object RefreshCompletedQuizzes : com.example.feature.movie_quiz.presentation.event.QuizCategoryEvent()
}