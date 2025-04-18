package com.example.movietheatre.feature_shop.data.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_shop.data.mapper.toData
import com.example.movietheatre.feature_shop.data.mapper.toDomain
import com.example.movietheatre.feature_shop.data.remote.dto.OrderRequest
import com.example.movietheatre.feature_shop.data.remote.service.OrderService
import com.example.movietheatre.feature_shop.domain.model.GetOrder
import com.example.movietheatre.feature_shop.domain.model.OrderItem
import com.example.movietheatre.feature_shop.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val orderService: OrderService,
) : OrderRepository {
    override suspend fun createOrder(
        userId: String,
        orderItems: List<OrderItem>,
    ): Resource<GetOrder, NetworkError> {
        return apiHelper.handleHttpRequest {
            orderService.createOrder(
                OrderRequest(userUid = userId, orderItems.map { it.toData() })
            )
        }.mapData { it.toDomain() }
    }
}