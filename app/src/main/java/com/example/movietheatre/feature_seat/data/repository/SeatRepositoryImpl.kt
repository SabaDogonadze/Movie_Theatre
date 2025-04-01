package com.example.movietheatre.feature_seat.data.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.model.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_seat.data.mapper.toDomain
import com.example.movietheatre.feature_seat.data.remote.service.SeatService
import com.example.movietheatre.feature_seat.domain.model.GetSeat
import com.example.movietheatre.feature_seat.domain.repository.SeatRepository
import javax.inject.Inject

class SeatRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val seatService: SeatService,
) :
    SeatRepository {
    override suspend fun getSeats(screeningId: Int): Resource<List<GetSeat>, NetworkError> {
        return apiHelper.handleHttpRequest { seatService.getSeats(screeningId) }.mapData {
            it.map { it.toDomain() }
        }
    }
}