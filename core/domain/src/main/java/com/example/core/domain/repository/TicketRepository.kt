package com.example.core.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError

interface TicketRepository {
    suspend fun updateTickets(
        screeningId: Int,
        seats: List<String>,
        status: String,
        userId: String,
        discount: Double,
    ): Resource<Unit, NetworkError>
}