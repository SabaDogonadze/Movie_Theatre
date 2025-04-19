package com.example.movietheatre.feature_shop.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_shop.domain.model.GetOrder
import com.example.movietheatre.feature_shop.domain.model.OrderItem

interface OrderRepository {

    suspend fun createOrder(
        userId: String,
        orderItems: List<OrderItem>,
    ): Resource<GetOrder, NetworkError>
}