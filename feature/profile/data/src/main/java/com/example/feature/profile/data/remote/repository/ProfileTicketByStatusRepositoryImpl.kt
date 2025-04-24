package com.example.feature.profile.data.remote.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.data.remote.mapper.toDomain
import com.example.feature.profile.data.remote.service.ProfileTicketService
import com.example.feature.profile.domain.model.UserTickets
import com.example.feature.profile.domain.repository.ProfileTicketByStatusRepository
import javax.inject.Inject

class ProfileTicketByStatusRepositoryImpl @Inject constructor(
    private val profileTicketService: ProfileTicketService,
    private val apiHelper: ApiHelper,
) :
    ProfileTicketByStatusRepository {

    override suspend fun getUsersTicketByStatus(
        userId: String,
        status: String,
    ): Resource<UserTickets, NetworkError> {
        return apiHelper.handleHttpRequest(
            apiCall = {
                profileTicketService.getUserTickets(userId = userId, status = status)
            }
        ).mapData { userTicketsDto -> userTicketsDto.toDomain() }

    }
}