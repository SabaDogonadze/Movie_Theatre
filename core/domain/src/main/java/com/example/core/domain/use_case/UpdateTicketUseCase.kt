package com.example.core.domain.use_case

import com.example.core.domain.repository.TicketRepository
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(private val ticketRepository: TicketRepository) {

    suspend operator fun invoke(
        screeningId: Int,
        seats: List<String>,
        status: String,
        userId: String,
        discount: Double = 0.0,
    ): Resource<Unit, NetworkError> {
        return ticketRepository.updateTickets(screeningId, seats, status, userId, discount)
    }
}