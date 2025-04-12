package com.example.movietheatre.core.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.UserTickets
import kotlinx.coroutines.flow.Flow

interface ProfileTicketByStatusRepository {
    fun getUsersTicketByStatus(userId:String,status:String): Flow<Resource<UserTickets, NetworkError>>
}