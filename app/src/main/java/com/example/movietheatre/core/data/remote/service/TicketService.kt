package com.example.movietheatre.core.data.remote.service

import com.example.movietheatre.core.data.remote.request.TicketRequest
import com.example.movietheatre.core.data.remote.response.TicketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface TicketService {
    @PUT("screening/{id}/seats")
    suspend fun updateTicket(
        @Path("id") id: Int,
        @Body request: TicketRequest,
    ): Response<TicketResponse>
}