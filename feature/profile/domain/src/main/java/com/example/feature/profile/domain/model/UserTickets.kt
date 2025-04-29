package com.example.feature.profile.domain.model


data class UserTickets(
    val tickets: List<Ticket>,
)

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

