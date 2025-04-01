package com.example.movietheatre.feature_seat.presentation.screen

import com.example.movietheatre.feature_seat.presentation.model.Seat

sealed interface SeatUiEvent {
    data object GetSeats : SeatUiEvent
    data class UpdateSeat(val seat: Seat) : SeatUiEvent
}