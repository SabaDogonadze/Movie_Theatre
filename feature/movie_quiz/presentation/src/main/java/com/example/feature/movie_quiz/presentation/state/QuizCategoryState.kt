package com.example.feature.movie_quiz.presentation.state

import com.example.feature.movie_quiz.presentation.model.QuizCategoryPresenter
import com.example.feature.movie_quiz.presentation.model.QuizCompletedPresenter

data class QuizCategoryState(
    val isLoading: Boolean = false,
    val categories: List<com.example.feature.movie_quiz.presentation.model.QuizCategoryPresenter> = emptyList(),
    val completedQuizzes: List<com.example.feature.movie_quiz.presentation.model.QuizCompletedPresenter> = emptyList(),
)