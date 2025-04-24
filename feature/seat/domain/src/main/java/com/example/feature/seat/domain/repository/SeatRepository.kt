package com.example.feature.seat.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.seat.domain.model.GetSeat

interface SeatRepository {

    suspend fun getSeats(screeningId: Int): Resource<List<GetSeat>, NetworkError>
}