package com.example.feature.seat.presentation.screen.seat

import com.example.feature.seat.presentation.model.Seat

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