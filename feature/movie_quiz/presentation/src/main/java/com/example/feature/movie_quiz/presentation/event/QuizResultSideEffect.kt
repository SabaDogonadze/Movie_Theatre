package com.example.feature.movie_quiz.presentation.event

sealed interface QuizResultSideEffect {

    data class ShowError(val message: Int) :
        com.example.feature.movie_quiz.presentation.event.QuizResultSideEffect
}