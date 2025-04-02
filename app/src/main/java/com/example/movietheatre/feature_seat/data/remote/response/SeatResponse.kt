package com.example.movietheatre.feature_seat.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SeatResponse(
    val id :Int,
    val seatNumber:String,
    val status:String,
    val vipAddOn:Double
)
