package com.example.feature.profile.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.domain.model.UserTickets

interface ProfileTicketByStatusRepository {
   suspend fun getUsersTicketByStatus(userId: String, status: String): Resource<UserTickets, NetworkError>
}