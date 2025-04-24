package com.example.feature.profile.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.domain.model.DeleteUsersTicket

interface DeleteUsersTicketRepository {
    suspend fun deleteUserTicket(bookingId: Int): Resource<DeleteUsersTicket, NetworkError>
}