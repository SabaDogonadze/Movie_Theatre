package com.example.movietheatre.feature_seat.presentation.screen

sealed interface SeatSideEffect {

    data class ShowError(val message: Int) : SeatSideEffect
    data object ShowSuccessfulHoldScreen : SeatSideEffect
    data object ShowSuccessfulBuyScreen : SeatSideEffect
    data object NavigateToDetailScreen : SeatSideEffect
}