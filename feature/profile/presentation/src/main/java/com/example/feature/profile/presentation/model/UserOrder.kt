package com.example.feature.profile.presentation.model

data class UserOrder(
    val orderId: Int,
    val trackingCode: String,
    val totalAmount: Double,
    val items: List<UserOrderItems>,
    val createdAt: String,
)


data class UserOrderItems(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val subtotal: Double,
    val imgUrl: String,
)
