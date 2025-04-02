package com.example.movietheatre.core.data.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.data.remote.request.TicketRequest
import com.example.movietheatre.core.data.remote.service.TicketService
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.repository.TicketRepository
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val ticketService: TicketService,
) : TicketRepository {
    override suspend fun updateTickets(
        screeningId: Int,
        seats: List<String>,
        status: String,
        userId: String,
    ): Resource<Unit, NetworkError> {
        return apiHelper.handleHttpRequest {
            ticketService.updateTicket(
                id = screeningId, request = TicketRequest(
                    seats, status, userId
                )
            )
        }.mapData { }
    }

}