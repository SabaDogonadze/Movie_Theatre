package com.example.feature.profile.domain.use_case

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.domain.model.DeleteUsersTicket
import com.example.feature.profile.domain.repository.DeleteUsersTicketRepository
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
