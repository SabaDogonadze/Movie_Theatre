package com.example.movietheatre.feature_seat.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_seat.domain.model.GetSeat

interface SeatRepository {

    suspend fun getSeats(screeningId: Int): Resource<List<GetSeat>, NetworkError>
}