package com.example.movietheatre.feature_seat.presentation.screen.seat

import com.example.movietheatre.feature_seat.presentation.model.Seat
import com.example.movietheatre.feature_seat.presentation.model.SeatTypeInfo
import com.example.movietheatre.feature_seat.presentation.util.SeatType

data class SeatUiState(
    val isLoading: Boolean = false,
    val seats: List<Seat> = emptyList(),
    val seatTypeInfo: List<SeatTypeInfo> = listOf(
        SeatTypeInfo(SeatType.BOOKED),
        SeatTypeInfo(SeatType.HELD),
        SeatTypeInfo(SeatType.FREE),
        SeatTypeInfo(SeatType.SELECTED)
    )
)