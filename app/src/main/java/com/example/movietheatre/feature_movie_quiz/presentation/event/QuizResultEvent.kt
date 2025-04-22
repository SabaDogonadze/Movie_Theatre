package com.example.movietheatre.feature_movie_quiz.presentation.event

sealed interface QuizResultEvent {

    data class UpdateUserCoin(val coins: Int) : QuizResultEvent
}