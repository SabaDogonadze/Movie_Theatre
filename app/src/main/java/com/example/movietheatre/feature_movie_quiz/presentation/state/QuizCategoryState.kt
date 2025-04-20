package com.example.movietheatre.feature_movie_quiz.presentation.state

import com.example.movietheatre.feature_movie_quiz.presentation.model.QuizCategoryPresenter
import com.example.movietheatre.feature_movie_quiz.presentation.model.QuizCompletedPresenter

data class QuizCategoryState(
    val isLoading: Boolean = false,
    val categories: List<QuizCategoryPresenter> = emptyList(),
    val completedQuizzes: List<QuizCompletedPresenter> = emptyList(),
    val error: String? = null
)