package com.example.movietheatre.feature_seat.presentation.screen

sealed interface SeatSideEffect {

    data class ShowError(val message: Int) : SeatSideEffect
}