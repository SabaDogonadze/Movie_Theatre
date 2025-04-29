package com.example.feature.seat.data.remote.service

import com.example.feature.seat.data.remote.response.SeatResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeatService {

    @GET("{id}/seats")
    suspend fun getSeats(@Path("id") screeningId: Int): Response<List<SeatResponse>>

    @GET("seats/{screeningId}")
    suspend fun getSeatsForPanorama(
        @Path("screeningId") screeningId: Int,
        @Query("seats") seats: String? = null,
    ): Response<List<SeatResponse>>
}