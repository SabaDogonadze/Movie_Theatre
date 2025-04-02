package com.example.movietheatre.feature_seat.domain.model

data class GetSeat(
    val id: Int,
    val seatNumber: String,
    val status: String,
    val vipAddOn: Double,
)