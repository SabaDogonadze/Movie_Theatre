package com.example.movietheatre.feature_seat.presentation.model

import com.example.movietheatre.feature_seat.presentation.util.SeatType

data class Seat(
    val id: Int,
    val seatNumber: String,
    val status: SeatType,
    val vipAddOn: Double,
)