package com.example.movietheatre.feature_movie_quiz.presentation.event

sealed class QuizCategoryEvent {
    data object LoadCategories : QuizCategoryEvent()
    data class SelectCategory(val categoryId: Int) : QuizCategoryEvent()
    data object RefreshCompletedQuizzes : QuizCategoryEvent()
}