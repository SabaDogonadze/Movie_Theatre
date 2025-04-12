package com.example.movietheatre.feature_profile.presentation.event

sealed class TicketBookedEvent {
    data class GetTickets(val userId:String,val ticketStatus:String) : TicketBookedEvent()
    data class DeleteTicket(val bookingId:Int):TicketBookedEvent()
    data object TicketItemClicked: TicketBookedEvent()
}