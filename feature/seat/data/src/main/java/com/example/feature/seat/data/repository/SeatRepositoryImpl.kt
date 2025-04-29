package com.example.feature.seat.data.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.seat.data.mapper.toDomain
import com.example.feature.seat.data.remote.service.SeatService
import com.example.feature.seat.domain.model.GetSeat
import com.example.feature.seat.domain.repository.SeatRepository
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

    override suspend fun getSeatsForPanorama(
        screeningId: Int,
        seats: String,
    ): Resource<List<GetSeat>, NetworkError> {
        return apiHelper.handleHttpRequest {
            seatService.getSeatsForPanorama(
                screeningId = screeningId,
                seats = seats
            )
        }.mapData {
            it.map { it.toDomain() }
        }
    }
}