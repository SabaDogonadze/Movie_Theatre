package com.example.feature.profile.presentation.event

sealed interface TicketHeldSideEffect {
    data class ShowError(val message: Int) :
        com.example.feature.profile.presentation.event.TicketHeldSideEffect
    data class NavigateToPaymentFragment(
        val screeningId: Int,
        val ticketPrice: Float,
        val seatNumbers: List<String>,
    ) :
        com.example.feature.profile.presentation.event.TicketHeldSideEffect

    data object NavigateToProfileFragment :
        com.example.feature.profile.presentation.event.TicketHeldSideEffect
    data object SuccessfulDelete :
        com.example.feature.profile.presentation.event.TicketHeldSideEffect
}