package com.example.feature.movie_quiz.presentation.event

sealed interface QuizResultEvent {

    data class UpdateUserCoin(val coins: Int) :
        com.example.feature.movie_quiz.presentation.event.QuizResultEvent
}