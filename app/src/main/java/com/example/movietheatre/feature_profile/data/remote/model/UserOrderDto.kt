package com.example.movietheatre.feature_profile.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserOrderDto(
    val orderId: Int,
    val trackingCode: String,
    val totalAmount: Double,
    val items: List<UserOrderItemsDto>,
    val createdAt: String,
)


@Serializable
data class UserOrderItemsDto(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val subtotal: Double,
    val imgUrl:String,
)
