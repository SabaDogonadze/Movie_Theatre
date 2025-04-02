package com.example.movietheatre.core.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError

interface TicketRepository {
    suspend fun updateTickets(
        screeningId: Int,
        seats: List<String>,
        status: String,
        userId: String,
    ): Resource<Unit, NetworkError>
}