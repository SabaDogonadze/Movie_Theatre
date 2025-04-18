package com.example.movietheatre.feature_shop.data.mapper

import com.example.movietheatre.feature_shop.data.remote.dto.OrderDto
import com.example.movietheatre.feature_shop.data.remote.dto.OrderItemDto
import com.example.movietheatre.feature_shop.domain.model.GetOrder
import com.example.movietheatre.feature_shop.domain.model.OrderItem

fun OrderDto.toDomain() = GetOrder(trackingCode)

fun OrderItem.toData() = OrderItemDto(productId, quantity)