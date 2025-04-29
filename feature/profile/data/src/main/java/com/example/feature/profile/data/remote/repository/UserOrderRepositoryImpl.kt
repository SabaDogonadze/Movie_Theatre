package com.example.feature.profile.data.remote.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.data.remote.mapper.toDomain
import com.example.feature.profile.data.remote.service.UserOrderService
import com.example.feature.profile.domain.model.GetUserOrder
import com.example.feature.profile.domain.repository.UserOrderRepository
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