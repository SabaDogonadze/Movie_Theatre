package com.example.movietheatre.feature_profile.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.data.remote.mapper.toDomain
import com.example.movietheatre.feature_profile.data.remote.service.ProfileTicketService
import com.example.movietheatre.feature_profile.domain.model.UserTickets
import com.example.movietheatre.core.domain.repository.ProfileTicketByStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileTicketByStatusRepositoryImpl @Inject constructor(private val profileTicketService: ProfileTicketService, private val apiHelper: ApiHelper) :
    ProfileTicketByStatusRepository {

    override fun getUsersTicketByStatus(userId: String,status:String): Flow<Resource<UserTickets, NetworkError>> {
        return flow {
            val resource = apiHelper.handleHttpRequest(
                apiCall = {
                    profileTicketService.getUserTickets(userId = userId,status = status)
                }
            ).mapData { userTicketsDto -> userTicketsDto.toDomain() }
            emit(resource)
        }
    }
}