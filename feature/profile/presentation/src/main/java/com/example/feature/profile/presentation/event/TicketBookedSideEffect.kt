package com.example.feature.profile.presentation.event

sealed interface TicketBookedSideEffect {
    data class ShowError(val message: Int) :
        com.example.feature.profile.presentation.event.TicketBookedSideEffect
    data object NavigateToQrFragment :
        com.example.feature.profile.presentation.event.TicketBookedSideEffect
    data object SuccessfulDelete :
        com.example.feature.profile.presentation.event.TicketBookedSideEffect
}