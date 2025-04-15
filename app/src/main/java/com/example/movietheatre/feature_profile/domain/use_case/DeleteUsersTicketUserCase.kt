package com.example.movietheatre.feature_profile.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.DeleteUsersTicket
import com.example.movietheatre.feature_profile.domain.repository.DeleteUsersTicketRepository
import javax.inject.Inject

interface DeleteUsersTicketUserCase {
    suspend operator fun invoke(bookingId: Int): Resource<DeleteUsersTicket, NetworkError>
}

class DeleteUsersTicketUserCaseImpl @Inject constructor(
    private val deleteUsersTicketRepository: DeleteUsersTicketRepository,
) : DeleteUsersTicketUserCase {
    override suspend fun invoke(bookingId: Int): Resource<DeleteUsersTicket, NetworkError> {
        return deleteUsersTicketRepository.deleteUserTicket(bookingId = bookingId)
    }
}
