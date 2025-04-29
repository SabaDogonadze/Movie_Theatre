package com.example.feature.seat.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SeatResponse(
    val id: Int,
    val seatNumber: String,
    val status: String,
    val imgUrl: String,
    val vipAddOn: Double,
)
