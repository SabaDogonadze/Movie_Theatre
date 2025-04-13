package com.example.movietheatre.feature_profile.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.UserTickets
import com.example.movietheatre.core.domain.repository.ProfileTicketByStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUserTicketsByStatusUseCase {
    operator fun invoke(userId: String, status: String): Flow<Resource<UserTickets, NetworkError>>
}

class GetUserTicketsByStatusUseCaseImpl @Inject constructor(
    private val profileTicketByStatusRepository: ProfileTicketByStatusRepository,
) : GetUserTicketsByStatusUseCase {
    override fun invoke(userId: String, status: String): Flow<Resource<UserTickets, NetworkError>> {
        return profileTicketByStatusRepository.getUsersTicketByStatus(
            userId = userId,
            status = status
        )
    }
}