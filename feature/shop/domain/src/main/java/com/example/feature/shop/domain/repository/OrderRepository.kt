package com.example.feature.shop.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.shop.domain.model.GetOrder
import com.example.feature.shop.domain.model.OrderItem

interface OrderRepository {

    suspend fun createOrder(
        userId: String,
        orderItems: List<OrderItem>,
    ): Resource<GetOrder, NetworkError>
}