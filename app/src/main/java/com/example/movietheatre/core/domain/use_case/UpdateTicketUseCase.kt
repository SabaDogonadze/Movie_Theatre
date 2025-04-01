package com.example.movietheatre.core.domain.use_case

import com.example.movietheatre.core.domain.repository.TicketRepository
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(private val ticketRepository: TicketRepository) {

    suspend operator fun invoke(
        screeningId: Int,
        seats: List<String>,
        status: String,
        userId: String,
    ): Resource<Unit, NetworkError> {
        return ticketRepository.updateTickets(screeningId, seats, status, userId)
    }
}