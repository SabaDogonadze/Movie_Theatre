package com.example.feature.profile.data.remote.service

import com.example.feature.profile.data.remote.model.DeleteUsersTicketDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteUsersTicketService {
    @DELETE("booking/{id}")
    suspend fun deleteUserTickets(
        @Path("id") bookingId: Int,
    ) : Response<DeleteUsersTicketDto>
}