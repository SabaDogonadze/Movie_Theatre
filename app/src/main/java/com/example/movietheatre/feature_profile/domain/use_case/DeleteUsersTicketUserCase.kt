package com.example.movietheatre.feature_profile.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.DeleteUsersTicket
import com.example.movietheatre.core.domain.repository.DeleteUsersTicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeleteUsersTicketUserCase {
    operator fun invoke(bookingId:Int): Flow<Resource<DeleteUsersTicket, NetworkError>>
}

class DeleteUsersTicketUserCaseImpl @Inject constructor(
    private val deleteUsersTicketRepository: DeleteUsersTicketRepository,
) : DeleteUsersTicketUserCase {
    override fun invoke(bookingId: Int): Flow<Resource<DeleteUsersTicket, NetworkError>> {
        return deleteUsersTicketRepository.deleteUserTicket(bookingId = bookingId)
    }
}
