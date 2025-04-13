package com.example.movietheatre.feature_profile.presentation.event

sealed class TicketBookedEvent {
    data object GetTickets : TicketBookedEvent()
    data class DeleteTicket(val bookingId:Int):TicketBookedEvent()
    data object TicketItemClicked: TicketBookedEvent()
}