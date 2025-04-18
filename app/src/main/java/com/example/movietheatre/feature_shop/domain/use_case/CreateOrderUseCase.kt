package com.example.movietheatre.feature_shop.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_shop.domain.model.GetOrder
import com.example.movietheatre.feature_shop.domain.model.OrderItem
import com.example.movietheatre.feature_shop.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(
        userId: String,
        orderItems: List<OrderItem>,
    ): Resource<GetOrder, NetworkError> {
        return orderRepository.createOrder(userId = userId, orderItems = orderItems)
    }
}