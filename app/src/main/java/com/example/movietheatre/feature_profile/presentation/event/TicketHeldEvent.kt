package com.example.movietheatre.feature_profile.presentation.event

sealed class TicketHeldEvent {
    data object GetTickets : TicketHeldEvent()
    data class TicketItemClicked(
        val screeningId: Int,
        val totalPrice: Float,
        val seatNumbers: List<String>,
    ) : TicketHeldEvent()

    data class UpdateTicket(
        val screeningId: Int,
        val seats: List<String>,
    ) : TicketHeldEvent()
}