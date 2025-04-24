package com.example.feature.seat.presentation.screen.seat

import com.example.feature.seat.presentation.model.Seat
import com.example.feature.seat.presentation.model.SeatTypeInfo
import com.example.feature.seat.presentation.util.SeatType

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