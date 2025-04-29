package com.example.feature.profile.data.remote.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.data.remote.mapper.toDomain
import com.example.feature.profile.data.remote.service.DeleteUsersTicketService
import com.example.feature.profile.domain.model.DeleteUsersTicket
import com.example.feature.profile.domain.repository.DeleteUsersTicketRepository
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