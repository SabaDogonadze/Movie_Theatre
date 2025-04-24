package com.example.feature.movie_quiz.presentation.event

sealed class QuizSideEffect {
    data object NavigateToResults : com.example.feature.movie_quiz.presentation.event.QuizSideEffect()
    data object ShowTimeUpDialog : com.example.feature.movie_quiz.presentation.event.QuizSideEffect()
    data object ShowWrongAnswerDialog : com.example.feature.movie_quiz.presentation.event.QuizSideEffect()
    data object NavigateToCategories : com.example.feature.movie_quiz.presentation.event.QuizSideEffect()
}
