package com.example.feature.shop.domain.use_case

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.shop.domain.model.GetOrder
import com.example.feature.shop.domain.model.OrderItem
import com.example.feature.shop.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(
        userId: String,
        orderItems: List<OrderItem>,
    ): Resource<GetOrder, NetworkError> {
        return orderRepository.createOrder(userId = userId, orderItems = orderItems)
    }
}