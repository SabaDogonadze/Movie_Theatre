package com.example.feature.profile.presentation.event

sealed class TicketBookedEvent {
    data object GetTickets : com.example.feature.profile.presentation.event.TicketBookedEvent()
    data class DeleteTicket(val bookingId:Int):
        com.example.feature.profile.presentation.event.TicketBookedEvent()
    data object TicketItemClicked: com.example.feature.profile.presentation.event.TicketBookedEvent()
}