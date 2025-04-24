package com.example.feature.profile.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTicketsDto(
    @SerialName("tickets") val tickets: List<TicketDto>,
)

@Serializable
data class TicketDto(
    @SerialName("bookingId") val bookingId: Int,
    @SerialName("movieTitle") val movieTitle: String,
    @SerialName("movieImgUrl") val movieImgUrl: String,
    @SerialName("screeningTime") val screeningTime: String,
    @SerialName("screeningId") val screeningId: Int,
    @SerialName("seatNumbers") val seatNumbers: String,
    @SerialName("seatType") val seatType: String,
    @SerialName("totalMoney") val totalMoney: Double,
)
