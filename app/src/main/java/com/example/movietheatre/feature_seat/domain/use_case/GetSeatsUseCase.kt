package com.example.movietheatre.feature_seat.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_seat.domain.model.GetSeat
import com.example.movietheatre.feature_seat.domain.repository.SeatRepository
import javax.inject.Inject

class GetSeatsUseCase @Inject constructor(private val seatRepository: SeatRepository) {

    suspend operator fun invoke(screeningId: Int): Resource<List<GetSeat>, NetworkError> {
        return seatRepository.getSeats(screeningId)
    }
}