package com.example.movietheatre.feature_profile.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.data.remote.mapper.toDomain
import com.example.movietheatre.feature_profile.data.remote.service.DeleteUsersTicketService
import com.example.movietheatre.feature_profile.domain.model.DeleteUsersTicket
import com.example.movietheatre.feature_profile.domain.repository.DeleteUsersTicketRepository
import javax.inject.Inject

class DeleteUsersTicketRepositoryImpl @Inject constructor(
    private val deleteUsersTicketService: DeleteUsersTicketService,
    private val apiHelper: ApiHelper,
) :
    DeleteUsersTicketRepository {

    override suspend fun deleteUserTicket(
        bookingId: Int,
    ): Resource<DeleteUsersTicket, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = {
                deleteUsersTicketService.deleteUserTickets(bookingId = bookingId)
            }
        ).mapData { deleteUsersTicketDto -> deleteUsersTicketDto.toDomain() }

    }
}