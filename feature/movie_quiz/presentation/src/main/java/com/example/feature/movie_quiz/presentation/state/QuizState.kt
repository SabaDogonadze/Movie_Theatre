package com.example.feature.movie_quiz.presentation.state

import com.example.feature.movie_quiz.presentation.model.QuizQuestionPresenter

data class QuizState(
    val isLoading: Boolean = false,
    val questions: List<com.example.feature.movie_quiz.presentation.model.QuizQuestionPresenter> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val currentQuestion: com.example.feature.movie_quiz.presentation.model.QuizQuestionPresenter? = null,
    val selectedAnswerId: String? = null,
    val timeRemaining: Int = 45,
    val hasAnswered: Boolean = false,
    val isCorrectAnswer: Boolean? = null,
    val correctAnswersCount: Int = 0,
    val quizCategoryId: Int = -1,
    val totalQuestions: Int = 0
)
