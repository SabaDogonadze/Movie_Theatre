package com.example.movietheatre.feature_profile.presentation.event

sealed class TicketHeldEvent {
    data class GetTickets(val userId:String,val ticketStatus:String) : TicketHeldEvent()
    data class DeleteTicket(val bookingId:Int):TicketHeldEvent()
    data class TicketItemClicked(val screeningId:Int,val totalPrice:Float,val seatNumbers:List<String>): TicketHeldEvent()
    data class UpdateTicket(
        val screeningId: Int,
        val seats: List<String>,
        val status : String,
        val userId: String
    ):TicketHeldEvent()
}