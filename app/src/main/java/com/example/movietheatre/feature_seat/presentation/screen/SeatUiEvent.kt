package com.example.movietheatre.feature_seat.presentation.screen

import com.example.movietheatre.feature_seat.presentation.model.Seat

sealed interface SeatUiEvent {
    data class GetSeats(val screeningId: Int) : SeatUiEvent
    data class UpdateSeat(val seat: Seat) : SeatUiEvent
    data class BookTicket(
        val screeningId: Int,
    ) : SeatUiEvent

    data class BuyTicket(
        val screeningId: Int,
        val ticketPrice: Double,
    ) : SeatUiEvent

}