package com.example.movietheatre.feature_profile.presentation.event

sealed interface TicketHeldSideEffect {
    data class ShowError(val message: Int) : TicketHeldSideEffect
    data class NavigateToPaymentFragment(
        val screeningId: Int,
        val ticketPrice: Float,
        val seatNumbers: List<String>,
    ) :
        TicketHeldSideEffect

    data object NavigateToProfileFragment : TicketHeldSideEffect
    data object SuccessfulDelete : TicketHeldSideEffect
}