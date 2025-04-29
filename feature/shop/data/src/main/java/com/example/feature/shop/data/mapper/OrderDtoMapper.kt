package com.example.feature.shop.data.mapper

import com.example.feature.shop.data.remote.dto.OrderDto
import com.example.feature.shop.data.remote.dto.OrderItemDto
import com.example.feature.shop.domain.model.GetOrder
import com.example.feature.shop.domain.model.OrderItem

fun OrderDto.toDomain() = GetOrder(trackingCode)

fun OrderItem.toData() = OrderItemDto(productId, quantity)