package com.example.movietheatre.feature_profile.presentation.event

sealed interface TicketBookedSideEffect {
    data class ShowError(val message: Int) : TicketBookedSideEffect
    data object NavigateToQrFragment : TicketBookedSideEffect
    data object SuccessfulDelete : TicketBookedSideEffect
}