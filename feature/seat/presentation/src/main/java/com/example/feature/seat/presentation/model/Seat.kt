package com.example.feature.seat.presentation.model

import com.example.feature.seat.presentation.util.SeatType

data class Seat(
    val id: Int,
    val seatNumber: String,
    val status: SeatType,
    val vipAddOn: Double,
    val imgUrl: String,
)