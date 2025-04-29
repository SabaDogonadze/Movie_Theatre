package com.example.feature.seat.domain.model

data class GetSeat(
    val id: Int,
    val seatNumber: String,
    val status: String,
    val vipAddOn: Double,
    val imgUrl: String,
)