package com.example.movietheatre.feature_shop.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemDto(
    val productId: Int,
    val quantity: Int,
)
