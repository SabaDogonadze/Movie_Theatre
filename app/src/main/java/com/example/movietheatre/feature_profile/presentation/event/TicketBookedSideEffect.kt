package com.example.movietheatre.feature_profile.presentation.event

sealed class TicketBookedSideEffect {
    data class ShowError(val message: Int) : TicketBookedSideEffect()
    data object NavigateToProfileFragment : TicketBookedSideEffect()
    data object NavigateToQrFragment : TicketBookedSideEffect()
    data object TicketUpdatedSuccessfully : TicketBookedSideEffect()
}