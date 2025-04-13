package com.example.movietheatre.feature_profile.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.model.UserTickets

interface ProfileTicketByStatusRepository {
   suspend fun getUsersTicketByStatus(userId: String, status: String): Resource<UserTickets, NetworkError>
}