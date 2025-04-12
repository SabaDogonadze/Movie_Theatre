package com.example.movietheatre.feature_profile.presentation.model

data class UserTicketsPresenter(
    val tickets: List<TicketPresenter>,
)

data class TicketPresenter(
    val bookingId: Int,
    val movieTitle: String,
    val movieImgUrl: String,
    val screeningTime: String,
    val screeningId: Int,
    val seatNumbers: String,
    val seatType: String,
    val totalMoney: Double,
)


