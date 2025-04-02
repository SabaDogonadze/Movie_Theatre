package com.example.movietheatre.feature_seat.data.remote.service

import com.example.movietheatre.feature_seat.data.remote.response.SeatResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeatService {

    @GET("{id}/seats")
    suspend fun getSeats(@Path("id") screeningId: Int): Response<List<SeatResponse>>
}