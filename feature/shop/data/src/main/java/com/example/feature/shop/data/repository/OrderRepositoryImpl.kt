package com.example.feature.shop.data.repository

import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.shop.data.mapper.toData
import com.example.feature.shop.data.mapper.toDomain
import com.example.feature.shop.data.remote.dto.OrderRequest
import com.example.feature.shop.data.remote.service.OrderService
import com.example.feature.shop.domain.model.GetOrder
import com.example.feature.shop.domain.model.OrderItem
import com.example.feature.shop.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiHelper: com.example.core.data.common.ApiHelper,
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