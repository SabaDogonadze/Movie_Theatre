package com.example.movietheatre.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class TicketResponse(
    val screeningId: Int,
    )