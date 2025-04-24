package com.example.feature.profile.presentation.event

sealed class TicketHeldEvent {
    data object GetTickets : com.example.feature.profile.presentation.event.TicketHeldEvent()
    data class TicketItemClicked(
        val screeningId: Int,
        val totalPrice: Float,
        val seatNumbers: List<String>,
    ) : com.example.feature.profile.presentation.event.TicketHeldEvent()

    data class UpdateTicket(
        val screeningId: Int,
        val seats: List<String>,
    ) : com.example.feature.profile.presentation.event.TicketHeldEvent()
}