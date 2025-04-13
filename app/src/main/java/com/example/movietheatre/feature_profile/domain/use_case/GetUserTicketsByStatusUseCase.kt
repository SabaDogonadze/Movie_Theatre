package com.example.movietheatre.feature_profile.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.UserTickets
import com.example.movietheatre.feature_profile.domain.repository.ProfileTicketByStatusRepository
import javax.inject.Inject

interface GetUserTicketsByStatusUseCase {
    suspend operator fun invoke(userId: String, status: String): Resource<UserTickets, NetworkError>
}

class GetUserTicketsByStatusUseCaseImpl @Inject constructor(
    private val profileTicketByStatusRepository: ProfileTicketByStatusRepository,
) : GetUserTicketsByStatusUseCase {
    override suspend fun invoke(
        userId: String,
        status: String,
    ): Resource<UserTickets, NetworkError> {
        return profileTicketByStatusRepository.getUsersTicketByStatus(
            userId = userId,
            status = status
        )
    }
}