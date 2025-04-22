package com.example.movietheatre.feature_movie_quiz.presentation.event

sealed interface QuizResultSideEffect {

    data class ShowError(val message: Int) : QuizResultSideEffect
}