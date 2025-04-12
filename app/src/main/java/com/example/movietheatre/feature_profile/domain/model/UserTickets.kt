package com.example.movietheatre.feature_profile.domain.model

import kotlinx.serialization.Serializable

data class UserTickets(
    val tickets: List<Ticket>,
)

@Serializable
data class Ticket(
    val bookingId: Int,
    val movieTitle: String,
    val movieImgUrl: String,
    val screeningTime: String,
    val screeningId: Int,
    val seatNumbers: String,
    val seatType: String,
    val totalMoney: Double,
)

