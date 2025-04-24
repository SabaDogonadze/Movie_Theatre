package com.example.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class TicketRequest(
    val seatNumbers: List<String>,
    val status: String,
    val userId: String,
    val discount: Double = 0.0,
)