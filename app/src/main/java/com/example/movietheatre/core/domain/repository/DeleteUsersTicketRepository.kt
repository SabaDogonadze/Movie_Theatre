package com.example.movietheatre.core.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.DeleteUsersTicket
import kotlinx.coroutines.flow.Flow

interface DeleteUsersTicketRepository {
    fun deleteUserTicket(bookingId:Int): Flow<Resource<DeleteUsersTicket, NetworkError>>
}