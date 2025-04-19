package com.example.movietheatre.feature_profile.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.data.remote.mapper.toDomain
import com.example.movietheatre.feature_profile.data.remote.service.UserOrderService
import com.example.movietheatre.feature_profile.domain.model.GetUserOrder
import com.example.movietheatre.feature_profile.domain.repository.UserOrderRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserOrderRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiHelper: ApiHelper,
    private val userOrderService: UserOrderService,
) : UserOrderRepository {
    override suspend fun getUserOrder(): Resource<List<GetUserOrder>, NetworkError> {
        return apiHelper.handleHttpRequest {
            userOrderService.getUserOrder(firebaseAuth.currentUser!!.uid)
        }.mapData {
            it.toDomain()
        }
    }
}