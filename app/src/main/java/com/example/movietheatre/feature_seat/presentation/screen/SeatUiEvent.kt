package com.example.movietheatre.feature_seat.presentation.screen

import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.feature_seat.presentation.model.Seat

sealed interface SeatUiEvent {
    data class GetSeats(val screeningId: Int) : SeatUiEvent
    data class UpdateSeat(val seat: Seat) : SeatUiEvent
    data class UpdateTicker(
        val screeningId: Int,
        val status: TicketStatus,
    ) : SeatUiEvent
}