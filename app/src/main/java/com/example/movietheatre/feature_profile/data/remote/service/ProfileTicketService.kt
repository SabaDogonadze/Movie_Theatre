package com.example.movietheatre.feature_profile.data.remote.service

import com.example.movietheatre.feature_profile.data.remote.model.UserTicketsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileTicketService {
    @GET("tickets")
    suspend fun getUserTickets(
        @Query("userId") userId: String,
        @Query("status") status: String,
        @Query("orderType") orderType: String = "asc"
    ) : Response<UserTicketsDto>
}