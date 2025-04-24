package com.example.feature.seat.presentation.screen.seat

sealed interface SeatSideEffect {

    data class ShowError(val message: Int) : SeatSideEffect
    data object ShowSuccessfulHoldScreen : SeatSideEffect
    data class NavigateToPaymentScreen(val seats: List<String>, val totalPrice: Double) :
        SeatSideEffect

    data object NavigateToDetailScreen : SeatSideEffect
}