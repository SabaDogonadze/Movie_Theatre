package com.example.feature.profile.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.profile.domain.model.GetUserOrder

interface UserOrderRepository {

    suspend fun getUserOrder(): Resource<List<GetUserOrder>, NetworkError>
}