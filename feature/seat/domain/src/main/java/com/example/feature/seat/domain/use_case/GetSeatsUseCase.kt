package com.example.feature.seat.domain.use_case

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.seat.domain.model.GetSeat
import com.example.feature.seat.domain.repository.SeatRepository
import javax.inject.Inject

class GetSeatsUseCase @Inject constructor(private val seatRepository: SeatRepository) {

    suspend operator fun invoke(screeningId: Int): Resource<List<GetSeat>, NetworkError> {
        return seatRepository.getSeats(screeningId)
    }
}